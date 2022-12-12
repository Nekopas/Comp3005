package com.example.Comp3005.controller;

import com.example.Comp3005.entity.*;
import com.example.Comp3005.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class StoreController {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/")
    public String StorePage() {
        return "login";
    }

    @RequestMapping("/manager/store/processing")
    public String StorePage2(String input, Model model,HttpSession session) {
        Long uid = (Long) session.getAttribute("uid");
        if(uid!=null){
            if(userMapper.findUserById(uid).size()==0){
                return "redirect:/login";
            }
        }
        //search book by comparing input with title, author,genre, isbn....
        if(input!=""){
            ArrayList<Book> books= bookMapper.findBookByInput(input);
            if (books.size() != 0) {
                model.addAttribute("books", books);
            }
        }
        return "store_manager";
    }

    @RequestMapping("/user/store/processing")
    public String StorePage3(Long uid,String input,Model model,HttpSession session) {
        if(uid==null){
            uid = (Long) session.getAttribute("uid");
        }else{
            session.setAttribute("uid",uid);
        }
        if(uid!=null){
            ArrayList<User> users=userMapper.findUserById(uid);
            model.addAttribute("user",users.get(0));
        }
        if(input!=""){
            ArrayList<Book> books= bookMapper.findBookByInput(input);
            if (books.size() != 0) {
                model.addAttribute("books", books);
            }
        }
        return "store";
    }


    @RequestMapping("/manager/store")
    public String StorePage4(Model model,HttpSession session) {
        Long uid = (Long) session.getAttribute("uid");
        if(uid!=null){
            if(userMapper.findUserById(uid).size()==0){
                return "store_manager";
            }
        }else{
            return "store_manager";
        }
        ArrayList<Book> books= bookMapper.findAllBook();
        if (books.size() != 0) {
            model.addAttribute("books", books);
        }
        return "store_manager";
    }

    @RequestMapping("/user/store")
    public String StorePage5(Long uid,Model model,HttpSession session) {
        if(uid==null){
            uid = (Long) session.getAttribute("uid");
        }else{
            session.setAttribute("uid",uid);
        }
        if(uid!=null){
            User user=userMapper.findUserById(uid).get(0);
            model.addAttribute("user",user);
        }
        ArrayList<Book> books= bookMapper.findAllBook();
        if (books.size() != 0) {
            model.addAttribute("books", books);
        }
        return "store";
    }





}
