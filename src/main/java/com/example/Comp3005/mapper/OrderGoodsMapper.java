package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.OrderGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface OrderGoodsMapper {
    public ArrayList<OrderGoods> findOrderGoodsById(long id);
    public ArrayList<OrderGoods> findOrderGoodsByGoodsId(long gid);
    public ArrayList<OrderGoods> findOrderGoodsByOrderId(long oid);
    public void insertOrderGoods(OrderGoods og);
    public void updateOrderGoods(OrderGoods og);
    public void deleteOrderGoodsById(long id);
    public void deleteOrderGoodsByOrderId(long oid);
}
