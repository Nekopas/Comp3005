package com.example.Comp3005;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/store").setViewName("store");
        registry.addViewController("/book").setViewName("book");
        registry.addViewController("/cart").setViewName("cart");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/order").setViewName("order");
        registry.addViewController("/pay").setViewName("pay");
        registry.addViewController("/").setViewName("store");
        registry.addViewController("/index").setViewName("store");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/manager/store").setViewName("store_manager");
        registry.addViewController("/user/store").setViewName("store");
        registry.addViewController("/order/detail").setViewName("orderInfo");
        registry.addViewController("/order/place").setViewName("orderplace");
        registry.addViewController("/manager/add").setViewName("book_add");
    }
}
