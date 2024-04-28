package com.fuswx.spzx.pay.mapper;

import com.fuswx.spzx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {

    //根据订单编号查询支付记录
    PaymentInfo getByOrderNo(String orderNo);

    //添加
    void save(PaymentInfo paymentInfo);

    //
    void updatePaymentInfo(PaymentInfo paymentInfo);
}
