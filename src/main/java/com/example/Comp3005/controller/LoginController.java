package com.example.Comp3005.controller;

import com.example.Comp3005.entity.User;
import com.example.Comp3005.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/login")
    public String Login() {
        return "login";
    }

    @RequestMapping("/login/processing")
    public String LoginProcess(String btnradio, String account, String password, Model model, HttpSession session) {
        ArrayList<User> users = userMapper.findUserByAccount(account);
        if(users.size()!=0){
            if(!users.get(0).getPassword().equals(password)){
                model.addAttribute("msg","Error Password");
                return "login";
            }else {
                if(!users.get(0).isAccess()&&btnradio.equals("0")){
                    session.setAttribute("uid",users.get(0).getId());
                    return "redirect:/user/store";
                }else if(users.get(0).isAccess()&&btnradio.equals("1")){
                    session.setAttribute("uid",users.get(0).getId());
                    return "redirect:/manager/store";
                }else{
                    model.addAttribute("msg","No exist account");
                    return "login";
                }
            }
        }else{
            model.addAttribute("msg","No exist account");
         return "login";
        }
    }

    @RequestMapping("/register")
    public String Register(Model model) {
        return "register";

    }

    @RequestMapping("/register/process")
    public String RegisterProcess(String account,String password,String name,String address,String gender,String phoneNumber,String email,Model model,HttpSession session) {
        if(userMapper.findUserByAccount(account).size()!=0){
            model.addAttribute("msg","Account Already Exist");
            return "register";
        }
        //initialize User object and insert to user table
        User user = new User();
        user.setAccess(false);
        user.setAccount(account);
        user.setPassword(password);
        user.setName(name);
        user.setAddress(address);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        userMapper.insertUser(user);
        Long uid=userMapper.findUserByAccount(account).get(0).getId();
        session.setAttribute("uid",uid);
        return "redirect:/user/store";
    }

    @RequestMapping("/signOut")
    public String SignOut(HttpSession session) {
        //clear uid from session
        session.setAttribute("uid",null);
        return "redirect:/login";
    }

}
