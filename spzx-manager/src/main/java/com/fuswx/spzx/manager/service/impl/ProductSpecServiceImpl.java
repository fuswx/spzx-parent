package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.manager.mapper.ProductSpecMapper;
import com.fuswx.spzx.manager.service.ProductSpecService;
import com.fuswx.spzx.model.entity.base.ProductUnit;
import com.fuswx.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Resource
    private ProductSpecMapper productSpecMapper;

    //列表
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> list = productSpecMapper.findByPage();
        return new PageInfo<>(list);
    }

    //添加
    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    //修改
    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.update(productSpec);
    }

    //删除
    @Override
    public void deleteById(Integer id) {
        productSpecMapper.delete(id);
    }

    //查找所有商品规格
    @Override
    public List<ProductUnit> findAll() {
        return productSpecMapper.findAll();
    }
}
