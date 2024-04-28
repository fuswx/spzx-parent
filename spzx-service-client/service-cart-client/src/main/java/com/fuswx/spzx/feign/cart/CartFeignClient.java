package com.fuswx.spzx.feign.cart;

import com.fuswx.spzx.model.entity.h5.CartInfo;
import com.fuswx.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart")
public interface CartFeignClient {

    @GetMapping("/api/order/cart/auth/getAllChecked")
    List<CartInfo> getAllChecked();

    @GetMapping("/api/order/cart/auth/deleteChecked")
    Result deleteChecked();
}
