package com.example.Comp3005.entity;

import lombok.Data;

@Data
public class BankAccount {
    private long id;
    private float balance;
    private long owner_id;
}
