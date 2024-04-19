package com.fuswx.spzx.product.service;

import com.fuswx.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {

    //所有一级分类
    List<Category> selectOneCategory();

    //查询所有分类，树形封装
    List<Category> findCategoryTree();
}
