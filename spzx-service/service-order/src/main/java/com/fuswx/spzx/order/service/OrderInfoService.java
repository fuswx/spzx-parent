package com.fuswx.spzx.order.service;

import com.fuswx.spzx.model.dto.h5.OrderInfoDto;
import com.fuswx.spzx.model.entity.order.OrderInfo;
import com.fuswx.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

public interface OrderInfoService {
    //结算
    TradeVo getTrade();

    //提交订单
    Integer submitOrder(OrderInfoDto orderInfoDto);

    //获取订单信息
    OrderInfo getOrderInfo(Integer orderId);

    //立即购买
    TradeVo buy(Integer skuId);

    //获取订单分页列表
    PageInfo<OrderInfo> findOrderByPage(Integer page, Integer limit, Integer orderStatus);

    //远程调用：根据订单编号获取订单信息
    OrderInfo getOrderInfoByOrderNo(String orderNo);

    //更新订单支付状态
    void updateOrderStatus(String orderNo, Integer orderStatus);
}
