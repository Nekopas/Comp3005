package com.example.Comp3005.entity;

import lombok.Data;

@Data
public class User {
    private long id;
    private String account,password,name,address,gender,phoneNumber,email;
    private boolean access;

}
