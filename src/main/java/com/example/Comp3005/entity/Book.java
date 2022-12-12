package com.example.Comp3005.entity;

import lombok.Data;

@Data
public class Book {
    private long id;
    private String title,author,isbn,genre;
    private int  numberOfPage,inStoreNumber,previousSale;
    private float price,percentage;
    private long publisher_id,bookstore_id;
}
