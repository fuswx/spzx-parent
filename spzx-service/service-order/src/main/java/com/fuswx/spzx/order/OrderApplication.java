package com.fuswx.spzx.order;

import com.fuswx.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.fuswx.spzx.common.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.fuswx.spzx"})
@EnableUserTokenFeignInterceptor
@EnableUserLoginAuthInterceptor
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
