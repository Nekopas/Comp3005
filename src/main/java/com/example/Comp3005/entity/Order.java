package com.example.Comp3005.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Order {
    private long id;
    private long user_id;
    private boolean shipState,receiveState,cancelled;
    private String shippingAddress,billingAddress;
}
