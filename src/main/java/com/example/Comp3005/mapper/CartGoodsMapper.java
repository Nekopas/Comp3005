package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.CartGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CartGoodsMapper {
    public ArrayList<CartGoods> findCartById(long id);
    public ArrayList<CartGoods> findCartByUserId(long uid);
    public ArrayList<CartGoods> findCartByGoodsIdandUserId(long bid,long uid);
    public void insertCart(CartGoods cg);
    public void updateCart(CartGoods cg);
    public void deleteCartById(long id);
    public void deleteCartByUserId(long uid);
}
