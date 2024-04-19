package com.fuswx.spzx.product.service.impl;

import com.fuswx.spzx.model.entity.product.Brand;
import com.fuswx.spzx.product.mapper.BrandMapper;
import com.fuswx.spzx.product.service.BrandService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    //获取全部品牌
    @Cacheable(value = "brand", key = "'all'")
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
