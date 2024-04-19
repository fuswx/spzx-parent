package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.manager.mapper.ProductUnitMapper;
import com.fuswx.spzx.manager.service.ProductUnitService;
import com.fuswx.spzx.model.entity.base.ProductUnit;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Resource
    private ProductUnitMapper productUnitMapper;

    //查找所有
    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}
