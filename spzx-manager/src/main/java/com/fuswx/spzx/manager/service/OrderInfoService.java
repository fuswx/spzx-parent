package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.dto.order.OrderStatisticsDto;
import com.fuswx.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
