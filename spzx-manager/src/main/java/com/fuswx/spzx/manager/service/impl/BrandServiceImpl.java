package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.manager.mapper.BrandMapper;
import com.fuswx.spzx.manager.service.BrandService;
import com.fuswx.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Brand> brandList = brandMapper.findByPage();
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
        return pageInfo;
    }

    //添加
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public void deleteById(Integer id) {
        brandMapper.deleteById(id);
    }

    //查询所有品牌
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
