package com.fuswx.spzx.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.fuswx.spzx.common.exception.FuswxException;
import com.fuswx.spzx.model.entity.pay.PaymentInfo;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.pay.service.AliPayService;
import com.fuswx.spzx.pay.service.PaymentInfoService;
import com.fuswx.spzx.pay.utils.AliPayProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Resource
    private PaymentInfoService paymentInfoService;

    @Resource
    private AliPayProperties aliPayProperties;

    @Resource
    private AlipayClient alipayClient;

    //支付宝下单
    @Override
    public String submitAliPay(String orderNo) {
        //1、保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        //2、调用支付宝服务接口
        //构建需要参数，进行调用
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        //同步回调
        alipayRequest.setReturnUrl(aliPayProperties.getReturnPaymentUrl());
        //异步回调
        alipayRequest.setNotifyUrl(aliPayProperties.getNotifyPaymentUrl());

        //准备请求参数，声明一个map集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no",paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount",new BigDecimal("0.01"));
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        //调用支付宝服务的接口
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()){
                return response.getBody();
            }else {
                throw new FuswxException(ResultCodeEnum.DATA_ERROR);
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
