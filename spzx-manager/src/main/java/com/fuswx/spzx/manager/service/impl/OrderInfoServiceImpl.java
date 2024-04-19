package com.fuswx.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fuswx.spzx.manager.mapper.OrderStatisticsMapper;
import com.fuswx.spzx.manager.service.OrderInfoService;
import com.fuswx.spzx.model.dto.order.OrderStatisticsDto;
import com.fuswx.spzx.model.entity.order.OrderStatistics;
import com.fuswx.spzx.model.vo.order.OrderStatisticsVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderStatisticsMapper orderStatisticsMapper;

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        //根据dto条件查询统计结果数据，返回list集合
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);

        //遍历list集合，得到所有日期，把所有日期封装list进新集合里面
        List<String> dataList = orderStatisticsList.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd")).collect(Collectors.toList());

        //遍历list集合，得到所有日期对应的总金额，把总金额封装list进新集合里面
        List<BigDecimal> decimalList = orderStatisticsList.stream().map(OrderStatistics::getTotalAmount).collect(Collectors.toList());

        //把两个list集合封装OrderStatisticsVo，返回OrderStatisticsVo
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dataList);
        orderStatisticsVo.setAmountList(decimalList);
        return orderStatisticsVo;
    }
}
