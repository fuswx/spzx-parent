package com.fuswx.spzx.product.service;

import com.fuswx.spzx.model.dto.h5.ProductSkuDto;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.fuswx.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {

    //根据销量排序，获取前10条记录
    List<ProductSku> selectProductSkuBySale();

    //分页查询
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    //商品详情
    ProductItemVo item(Integer skuId);

    //远程调用：根据skuId返回sku信息
    ProductSku getBySkuId(Integer skuId);
}
