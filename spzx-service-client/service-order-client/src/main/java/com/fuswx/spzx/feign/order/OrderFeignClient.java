package com.fuswx.spzx.feign.order;

import com.fuswx.spzx.model.entity.order.OrderInfo;
import com.fuswx.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-order")
public interface OrderFeignClient {
    //远程调用：根据订单编号获取订单信息
    @GetMapping("/api/order/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    OrderInfo getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo);

    //
    @GetMapping("/api/order/orderInfo/auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    Result updateOrderStatusPayed(@PathVariable(value = "orderNo") String orderNo, @PathVariable(value = "orderStatus") Integer orderStatus);

}
