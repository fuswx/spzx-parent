package com.fuswx.spzx.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.fuswx.spzx.feign.order.OrderFeignClient;
import com.fuswx.spzx.model.entity.order.OrderInfo;
import com.fuswx.spzx.model.entity.pay.PaymentInfo;
import com.fuswx.spzx.pay.mapper.PaymentInfoMapper;
import com.fuswx.spzx.pay.service.PaymentInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Resource
    private PaymentInfoMapper paymentInfoMapper;

    @Resource
    private OrderFeignClient orderFeignClient;

    //保存支付记录
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        //1、根据订单编号查询支付记录
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);

        //2、判断支付记录是否存在
        if (paymentInfo == null) {
            //远程调用获取订单信息
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            //把orderInfo获取的数据封装到paymentInfo里面
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            paymentInfo.setContent(orderInfo.getOrderItemList().stream().reduce((a, b) -> {
                a.setSkuName(a.getSkuName() + b.getSkuName() + " ");
                return a;
            }).get().getSkuName());
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setContent(orderInfo.getOrderItemList().stream().map(orderItem -> orderItem.getSkuName()).collect(Collectors.joining(" ")));
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            //添加
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }

    //支付完成之后去更新状态
    @Override
    public void updatePaymentStatus(Map<String, String> paramMap) {
    //paramMap.get("out_trade_no")
        //1、根据订单编号查询支付记录信息
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(paramMap.get("out_trade_no"));

        //2、判断如果支付记录已经完成，不需要更新
        if (paymentInfo.getPaymentStatus() == 1){
            return;
        }

        //3、没有完成才更新
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(paramMap.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(paramMap));

        paymentInfoMapper.updatePaymentInfo(paymentInfo);

        //更新订单状态
        orderFeignClient.updateOrderStatusPayed(paymentInfo.getOrderNo(), 2);

        //TODO 更新sku商品销量......
    }
}
