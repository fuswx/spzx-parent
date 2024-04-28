package com.fuswx.spzx.order.mapper;

import com.fuswx.spzx.model.entity.order.OrderInfo;
import com.fuswx.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {

    //添加数据到order_info表
    void save(OrderInfo orderInfo);

    //获取订单信息
    OrderInfo getById(Integer orderId);

    //根据userId和订单状态进行查询
    List<OrderInfo> findUserPage(Integer userId, Integer orderStatus);

    //远程调用：根据订单编号获取订单信息
    OrderInfo getOrderInfoByOrderNo(String orderNo);

    //更新订单信息
    void updateById(OrderInfo orderInfo);
}
