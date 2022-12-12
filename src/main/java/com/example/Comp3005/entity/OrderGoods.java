package com.example.Comp3005.entity;

import lombok.Data;

@Data
public class OrderGoods {
    private long id;
    private long goods_id,order_id;
    private int number;
}
