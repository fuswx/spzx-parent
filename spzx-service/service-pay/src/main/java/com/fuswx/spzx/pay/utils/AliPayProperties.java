package com.fuswx.spzx.pay.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spzx.alipay")
public class AliPayProperties {

    private String alipayUrl;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String appId;
    private String returnPaymentUrl;
    private String notifyPaymentUrl;

    public final static String format = "json";
    public final static String charset = "utf-8";
    public final static String signType = "RSA2";
}
