package com.example.Comp3005.controller;

import com.example.Comp3005.entity.*;
import com.example.Comp3005.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class OrderController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CartGoodsMapper cartGoodsMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BankAccountMapper bankAccountMapper;
    @Autowired
    BookStoreMapper bookStoreMapper;

    @RequestMapping("/order")
    public String orderPage(Model model,HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
        if(uid==null){
            model.addAttribute("msg","no uid or bid");
            return "login";
        }else{
            ArrayList<Order> olist=orderMapper.findOrderByUserId(uid);
            model.addAttribute("orders",olist);
            return "order";
        }
    }

    @RequestMapping("/order/detail")
    public String orderPage3(Long oid, Model model,HttpSession session){
        if(oid==null){
            oid= (Long) session.getAttribute("oid");
        }
        Long uid =  (Long)session.getAttribute("uid");
        if(uid==null){
            model.addAttribute("msg","no uid or bid");
            return "login";
        }else{
            if(oid!=null){
                ArrayList<Order> order = orderMapper.findOrderById(oid);
                if(order.size()==0){
                    model.addAttribute("msg","no exist order");
                    return "store_manager";
                }
                model.addAttribute("order",order.get(0));
                ArrayList<OrderGoods> ordergoods= orderGoodsMapper.findOrderGoodsByOrderId(oid);
                if(ordergoods.size()!=0){
                    model.addAttribute("ordergoods",ordergoods);
                }else{
                    return "redirect:/order";
                }
                session.setAttribute("oid",order.get(0).getId());
            }
            User user = userMapper.findUserById(uid).get(0);
            model.addAttribute("user",user);
            return "orderInfo";
        }
    }

    @RequestMapping("/order/place/processing")
    public String orderPage4(String shippingAddress,String billingAddress,Model model, HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
        if(uid==null){
            model.addAttribute("msg","no uid or bid");
            return "login";
        }else{
            ArrayList<User> users=userMapper.findUserById(uid);
            if(users.size()!=0){
                //initialize new order object
                Order order = new Order();
                order.setUser_id(uid);
                order.setCancelled(false);
                order.setShipState(false);
                order.setReceiveState(false);
                order.setBillingAddress(shippingAddress);
                order.setShippingAddress(billingAddress);
                orderMapper.insertOrder(order);
                ArrayList<Order> orders = orderMapper.findOrderByUserId(uid);
                if(orders.size()==0){
                    System.out.println("Error order list");
                    return "redirect:/order";
                }
                //re-get the order
                order = orderMapper.findOrderByUserId(uid).get(orderMapper.findOrderByUserId(uid).size()-1);
                ArrayList<CartGoods> cgs=cartGoodsMapper.findCartByUserId(uid);
                for(int i=0;i<cgs.size();i++){
                    ArrayList<Book> books=bookMapper.findBookById(cgs.get(i).getGoods_id());
                    if(books.size()==0){
                        System.out.println("Error order list");
                        return "redirect:/order";
                    }
                    Book book = books.get(0);
                    //check available number left
                    if(book.getInStoreNumber()<cgs.get(i).getNumber()){
                        System.out.println("Out of inStoreNumber");
                        continue;
                    }
                    //initialize new ordergoods object corresponding to cartgoods
                    OrderGoods og= new OrderGoods();
                    og.setOrder_id(order.getId());
                    og.setNumber(cgs.get(i).getNumber());
                    og.setGoods_id(cgs.get(i).getGoods_id());
                    orderGoodsMapper.insertOrderGoods(og);
                    //update book number left
                    book.setInStoreNumber(book.getInStoreNumber()-og.getNumber());
                    //update previous sale number
                    book.setPreviousSale(book.getPreviousSale()+og.getNumber());
                    //check left number touch the minimun number, if yes update the instore number
                    if(book.getInStoreNumber()< bookStoreMapper.findAllBookStore().get(0).getMinNum()){
                        book.setInStoreNumber(bookStoreMapper.findAllBookStore().get(0).getMaxNum());
                    }
                    //re-get the OrderGoods
                    ArrayList<OrderGoods> ogs=orderGoodsMapper.findOrderGoodsByOrderId(order.getId());
                    og = ogs.get(ogs.size()-1);
                    //update publisher's bankaccount
                    BankAccount ba=bankAccountMapper.findBankAccountByOwnerId(book.getPublisher_id()).get(0);
                    ba.setBalance(ba.getBalance()+book.getPrice()*book.getPercentage()*og.getNumber());
                    bankAccountMapper.updateBankAccount(ba);
                    bookMapper.updateBook(book);
                }
                //clear user's cart
                cartGoodsMapper.deleteCartByUserId(uid);
            }else{
                session.setAttribute("uid",null);
                model.addAttribute("msg","error uid");
                return "login";
            }
        }
        return "redirect:/order";
    }

    @RequestMapping("/order/place")
    public String PlaceOrder(Model model, HttpSession session) {
        Long uid = (Long) session.getAttribute("uid");
        if (uid == null) {
            model.addAttribute("msg", "no uid or bid");
            return "login";
        } else {
            //pass cartgoods list to order place page
            ArrayList<CartGoods> cgs = cartGoodsMapper.findCartByUserId(uid);
            model.addAttribute("cartgoods", cgs);
            return "orderplace";
        }
    }

    @RequestMapping("/order/detail/receive")
    public String UserReceive(HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
        if(uid==null){
            return "redirect:/login";
        }
        Long oid =  (Long)session.getAttribute("oid");
        if(oid!=null){
            Order order = orderMapper.findOrderById(oid).get(0);
            //check order is shipped, then confirm received
            if(order.isShipState()){
                order.setReceiveState(true);
                orderMapper.updateOrder(order);
            }
        }
        return "redirect:/order/detail";
    }

    @RequestMapping("/order/detail/ship")
    public String UserShip(HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
        if(uid==null){
            return "redirect:/login";
        }
        Long oid =  (Long)session.getAttribute("oid");
        if(oid!=null){
            Order order = orderMapper.findOrderById(oid).get(0);
            //check order is shipped, then confirm received
            order.setShipState(true);
            orderMapper.updateOrder(order);
        }
        return "redirect:/order/detail";
    }

    @RequestMapping("/order/detail/cancel")
    public String UserCancel(Long oid,HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
        if(uid==null){
            return "redirect:/login";
        }
        if(oid==null){
            oid= (Long) session.getAttribute("oid");
        }
        if(oid!=null){
            Order order = orderMapper.findOrderById(oid).get(0);
            if(!order.isShipState()){
                order.setCancelled(true);
                orderMapper.updateOrder(order);
                ArrayList<OrderGoods> orderGoods=orderGoodsMapper.findOrderGoodsByOrderId(oid);
                //return each ordergoods to book number.
                for(int i=0;i<orderGoods.size();i++){
                    ArrayList<Book> books=bookMapper.findBookById(orderGoods.get(i).getGoods_id());
                    if(books.size()==0){
                        return "redirect:/order";
                    }
                    //update in store number
                    books.get(0).setInStoreNumber(books.get(0).getInStoreNumber()+orderGoods.get(i).getNumber());
                    //update previous sale
                    books.get(0).setPreviousSale(books.get(0).getPreviousSale()-orderGoods.get(i).getNumber());
                    bookMapper.updateBook(books.get(0));
                    //update publisher bankaccount
                    BankAccount ba = bankAccountMapper.findBankAccountByOwnerId(books.get(0).getPublisher_id()).get(0);
                    ba.setBalance(ba.getBalance()-books.get(0).getPrice()*books.get(0).getPercentage()*orderGoods.get(i).getNumber());
                    bankAccountMapper.updateBankAccount(ba);
                }
            }
        }
        return "redirect:/order/detail";
    }
}
