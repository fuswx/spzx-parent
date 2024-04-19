package com.fuswx.spzx.product.service;

import com.fuswx.spzx.model.entity.product.Brand;

import java.util.List;

public interface BrandService {
    //获取全部品牌
    List<Brand> findAll();
}
