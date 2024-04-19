package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.manager.mapper.CategoryBrandMapper;
import com.fuswx.spzx.manager.service.CategoryBrandService;
import com.fuswx.spzx.model.dto.product.CategoryBrandDto;
import com.fuswx.spzx.model.entity.product.Brand;
import com.fuswx.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    //分类品牌条件分页查询
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page, limit);
        List<CategoryBrand> list = categoryBrandMapper.findByPage(categoryBrandDto);
        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //添加
    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    //根据分类id查询对应品牌数据
    @Override
    public List<Brand> findBrandByCategoryId(Integer categoryId) {
        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
