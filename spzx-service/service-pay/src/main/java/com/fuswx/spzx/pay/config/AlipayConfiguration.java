package com.fuswx.spzx.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.fuswx.spzx.pay.utils.AliPayProperties;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfiguration {

    @Resource
    private AliPayProperties aliPayProperties;

    @Bean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperties.getAlipayUrl(),
                aliPayProperties.getAppId(),
                aliPayProperties.getAppPrivateKey(),
                AliPayProperties.format,
                AliPayProperties.charset,
                aliPayProperties.getAlipayPublicKey(),
                AliPayProperties.signType);
        return alipayClient;
    }

}
