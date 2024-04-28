package com.fuswx.spzx.pay.service;

import com.fuswx.spzx.model.entity.pay.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService {

    //保存支付记录
    PaymentInfo savePaymentInfo(String orderNo);

    //支付完成之后去更新状态
    void updatePaymentStatus(Map<String, String> paramMap);
}
