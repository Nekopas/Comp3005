package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface OrderMapper {
    public ArrayList<Order> findOrderById(long id);
    public ArrayList<Order> findOrderByUserId(long uid);
    public void insertOrder(Order o);
    public void updateOrder(Order o);
    public void deleteOrderById(long id);
    public void deleteOrderByUserId(long uid);
}
