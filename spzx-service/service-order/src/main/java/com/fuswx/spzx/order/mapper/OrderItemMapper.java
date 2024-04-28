package com.fuswx.spzx.order.mapper;

import com.fuswx.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    //添加数据到order_item表
    void save(OrderItem orderItem);

    //根据订单id查询订单里面订单项
    List<OrderItem> findByOrderId(Integer id);
}
