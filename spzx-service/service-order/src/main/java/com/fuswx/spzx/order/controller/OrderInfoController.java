package com.fuswx.spzx.order.controller;

import com.fuswx.spzx.model.dto.h5.OrderInfoDto;
import com.fuswx.spzx.model.entity.order.OrderInfo;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.h5.TradeVo;
import com.fuswx.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    @Operation(summary = "确认下单", description = "确认下单接口")
    @GetMapping("/auth/trade")
    public Result trade(){
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "提交订单", description = "提交订单接口")
    @PostMapping("/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
        Integer orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单信息", description = "获取订单信息接口")
    @GetMapping("/auth/{orderId}")
    public Result getOrderInfo(@PathVariable Integer orderId){
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "立即购买", description = "立即购买接口")
    @GetMapping("/auth/buy/{skuId}")
    public Result buy(@PathVariable Integer skuId){
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单分页列表", description = "获取订单分页列表接口")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Integer page, @PathVariable Integer limit, @RequestParam(required = false, defaultValue = "") Integer orderStatus){
        PageInfo<OrderInfo> pageInfo = orderInfoService.findOrderByPage(page, limit,  orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //远程调用：根据订单编号获取订单信息
    @Operation(summary = "根据订单编号获取订单信息", description = "远程调用：根据订单编号获取订单信息")
    @GetMapping("/auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo){
        return orderInfoService.getOrderInfoByOrderNo(orderNo);
    }

    @Operation(summary = "更新订单支付状态", description = "更新订单支付状态接口")
    @GetMapping("/auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    public Result updateOrderStatusPayed(@PathVariable(value = "orderNo") String orderNo, @PathVariable(value = "orderStatus") Integer orderStatus){
        orderInfoService.updateOrderStatus(orderNo, orderStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
