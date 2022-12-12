package com.example.Comp3005.entity;


import lombok.Data;

@Data
public class CartGoods {
    private long id;
    private long user_id,goods_id;
    private int number;
}
