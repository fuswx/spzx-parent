package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void update(Brand brand);

    void deleteById(Integer id);

    //查询所有品牌
    List<Brand> findAll();
}
