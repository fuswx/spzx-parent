package com.fuswx.spzx.feign.product;

import com.fuswx.spzx.model.entity.order.OrderInfo;
import com.fuswx.spzx.model.entity.product.ProductSku;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product")
public interface ProductFeignClient {

    //根据skuId返回sku信息
    @GetMapping("/api/product/getBySkuId/{skuId}")
    ProductSku getBySkuId(@PathVariable("skuId") Integer skuId);
}
