package com.fuswx.spzx.pay.service;

public interface AliPayService {
    //支付宝下单
    String submitAliPay(String orderNo);
}
