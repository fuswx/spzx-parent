package com.fuswx.spzx.order.mapper;

import com.fuswx.spzx.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper {

    //添加数据到order_log表
    void save(OrderLog orderLog);
}
