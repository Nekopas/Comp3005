package com.example.Comp3005.controller;

import com.example.Comp3005.entity.CartGoods;
import com.example.Comp3005.mapper.CartGoodsMapper;
import com.example.Comp3005.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class CartController {
    @Autowired
    CartGoodsMapper cartGoodsMapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/cart")
    public String cartPage(Model model,HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
//        System.out.println(uid);
        if(uid!=null){
            ArrayList<CartGoods> carts= cartGoodsMapper.findCartByUserId(uid);
            if(carts.size()!=0){
                model.addAttribute("carts",carts);
            }
        }else{
            return "redirect:/user/store";
        }
        model.addAttribute("user",userMapper.findUserById(uid).get(0));
        return "cart";
    }

    @RequestMapping("/cart/processing")
    public String AddCart(int number, Model model,HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
        Long bid =  (Long)session.getAttribute("bid");
        if(uid==null||bid==null){
            model.addAttribute("msg","no uid or bid");
            return "store";
        }else{
            ArrayList<CartGoods> cgs = cartGoodsMapper.findCartByGoodsIdandUserId(bid,uid);
            if(cgs.size()==0){
                CartGoods cg=new CartGoods();
                cg.setUser_id(uid);
                cg.setGoods_id(bid);
                cg.setNumber(number);
                cartGoodsMapper.insertCart(cg);
            }else{
                CartGoods cg=cgs.get(0);
                cg.setNumber(cg.getNumber()+number);
                cartGoodsMapper.updateCart(cg);
            }

        }
        return "redirect:/cart";
    }

    @RequestMapping("/cart/delete/processing")
    public String remove(Long cgid,int number, Model model,HttpSession session){
        Long uid =  (Long)session.getAttribute("uid");
        if(uid==null||cgid==null){
            ArrayList<CartGoods> cgs = cartGoodsMapper.findCartById(cgid);
            model.addAttribute("carts",cgs);
            model.addAttribute("msg","no uid or cgid");
            return "cart";
        }else{
            ArrayList<CartGoods> cgs = cartGoodsMapper.findCartById(cgid);
            if(cgs.size()==0){
                return "redirect:/cart";
            }else{
                CartGoods cg = cgs.get(0);
                if(cg.getNumber()<=number){
                    cartGoodsMapper.deleteCartById(cg.getId());
                }else{
                    cg.setNumber(cg.getNumber()-number);
                    cartGoodsMapper.updateCart(cg);
                }
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping("/cart/search/processing")
    public String search(Long cgid,HttpSession session){
        ArrayList<CartGoods> cgs = cartGoodsMapper.findCartById(cgid);
        if(cgs.size()==0){
            return "redirect:/cart";
        }else{
            CartGoods cg = cgs.get(0);
//            System.out.println("cg:"+cg.getId()+" 's good id:"+ cg.getGoods_id());
            session.setAttribute("bid",cg.getGoods_id());
//            System.out.println(session.getAttribute("bid"));
            return "redirect:/book";
        }
    }


}
