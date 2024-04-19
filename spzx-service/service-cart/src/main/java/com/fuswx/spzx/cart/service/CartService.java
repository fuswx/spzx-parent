package com.fuswx.spzx.cart.service;

import com.fuswx.spzx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {
    //添加购物车
    void addToCart(Integer skuId, Integer skuNum);

    //查询购物车
    List<CartInfo> getCartList();

    //删除购物车商品
    void deleteCart(Integer skuId);

    //更新购物车商品选中状态
    void checkCart(Integer skuId, Integer isChecked);

    //更新购物车商品全部选中状态
    void allCheckCart(Integer isChecked);

    //清空购物车
    void clearCart();
}
