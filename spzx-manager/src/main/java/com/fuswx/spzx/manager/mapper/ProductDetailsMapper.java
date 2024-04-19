package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {

    //商品详情的添加
    void save(ProductDetails productDetails);

    //根据商品id查询商品详情数据 product_details
    ProductDetails findProductDetailsById(Integer id);

    //修改product_details
    void updateById(ProductDetails productDetails);

    //根据商品id删除product_details表
    void deleteByProductId(Integer id);
}
