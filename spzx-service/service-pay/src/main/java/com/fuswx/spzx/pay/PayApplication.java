package com.fuswx.spzx.pay;

import com.fuswx.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.fuswx.spzx.pay.utils.AliPayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {"com.fuswx.spzx"})
@EnableConfigurationProperties(value = {AliPayProperties.class})
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
