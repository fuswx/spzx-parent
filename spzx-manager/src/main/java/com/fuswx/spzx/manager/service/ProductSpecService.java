package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.entity.base.ProductUnit;
import com.fuswx.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductSpecService {

    //列表
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    //添加
    void save(ProductSpec productSpec);

    //修改
    void updateById(ProductSpec productSpec);

    //删除
    void deleteById(Integer id);

    //查找所有商品规格
    List<ProductUnit> findAll();
}
