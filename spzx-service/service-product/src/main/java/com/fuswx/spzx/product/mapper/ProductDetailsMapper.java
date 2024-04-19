package com.fuswx.spzx.product.mapper;

import com.fuswx.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    //productId获取商品详情信息
    ProductDetails getByProductId(Integer productId);
}
