package com.fuswx.spzx.cart.controller;

import com.fuswx.spzx.cart.service.CartService;
import com.fuswx.spzx.model.entity.h5.CartInfo;
import com.fuswx.spzx.model.entity.user.UserInfo;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "购物车接口")
@RestController
@RequestMapping("/api/order/cart")
public class CartController {

    @Resource
    private CartService cartService;

    //skuId：商品sku的id值
    //skuNum：商品数量
    @Operation(summary = "添加购物车", description = "添加购物车接口")
    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Integer skuId,
                            @Parameter(name = "skuNum", description = "数量", required = true) @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询购物车", description = "查询购物车接口")
    @GetMapping("/auth/cartList")
    public Result cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除购物车商品")
    @DeleteMapping("/auth/deleteCart/{skuId}")
    public Result deleteCart(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Integer skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新购物车商品选中状态", description = "更新购物车商品选中状态接口")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Integer skuId,
                            @Parameter(name = "isChecked", description = "是否选中 1：选中 0：取消选中") @PathVariable("isChecked") Integer isChecked) {
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新购物车商品全部选中状态", description = "更新购物车商品全部选中状态接口")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@Parameter(name = "isChecked", description = "是否选中 1：选中 0：未选中", required = true) @PathVariable("isChecked") Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "清空购物车", description = "清空购物车接口")
    @GetMapping("/auth/clearCart")
    public Result clearCart() {
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "选中的购物车", description = "远程调用：订单结算使用，获取购物车选中商品列表")
    @GetMapping("/auth/getAllChecked")
    public List<CartInfo> getAllChecked() {
        List<CartInfo> cartInfoList = cartService.getAllChecked();
        return cartInfoList;
    }

    //远程调用：删除生成订单的购物车商品
    @Operation(summary = "远程调用：删除生成订单的购物车商品", description = "远程调用：删除生成订单的购物车商品接口")
    @GetMapping("/auth/deleteChecked")
    public Result deleteChecked() {
        cartService.deleteChecked();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
