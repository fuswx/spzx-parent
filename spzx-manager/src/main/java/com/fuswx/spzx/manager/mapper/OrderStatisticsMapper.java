package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.dto.order.OrderStatisticsDto;
import com.fuswx.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {

    //3、把统计之后的数据，添加到统计结果表里面
    void insert(OrderStatistics orderStatistics);

    //根据dto条件查询统计结果数据，返回list集合
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}
