package com.example.Comp3005.entity;

import lombok.Data;

@Data
public class BookStore {
    private long id;
    private String name;
    private int maxNum,minNum;
}
