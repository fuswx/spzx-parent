package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.OrderInfoService;
import com.fuswx.spzx.model.dto.order.OrderStatisticsDto;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.order.OrderStatisticsVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/order/orderInfo")
@Tag(name = "订单信息", description = "有关订单信息的接口")
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    @GetMapping("/getOrderStatisticsData")
    public Result getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto){
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }

}
