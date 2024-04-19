package com.fuswx.spzx.product.mapper;

import com.fuswx.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    //从sku获取productId，获取商品信息
    Product getById(Integer productId);

}
