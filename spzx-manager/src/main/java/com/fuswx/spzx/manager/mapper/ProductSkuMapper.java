package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    //保存
    void save(ProductSku productSku);

    //根据id查询商品sku信息列表 product_sku表
    List<ProductSku> findProductSkuByProductId(Integer id);

    //修改product_sku
    void updateById(ProductSku productSku);

    //根据商品id删除product_sku表
    void deleteByProductId(Integer id);
}
