package com.fuswx.spzx.product.service.impl;

import com.alibaba.fastjson2.JSON;
import com.fuswx.spzx.model.entity.product.Category;
import com.fuswx.spzx.product.mapper.CategoryMapper;
import com.fuswx.spzx.product.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //所有一级分类
    //category:one
    @Override
    public List<Category> selectOneCategory() {
        //1、查询redis，是否有所有一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");

        //2、如果redis包含所有一级分类，直接返回
        if (StringUtils.hasText(categoryOneJson)) {
            //categoryOneJson字符串转换成List<Category>
            return JSON.parseArray(categoryOneJson, Category.class);
        }

        //3、如果redis没有所有一级分类，查询数据库，把数据库查询内容返回，并且把查询内容放到redis里面
        List<Category> categoryList = categoryMapper.selectOneCategory();
        redisTemplate.opsForValue().set("category:one", JSON.toJSONString(categoryList), 7, TimeUnit.DAYS);
        return categoryList;
    }

    //查询所有分类，树形封装
    //category::all
    @Cacheable(value = "category", key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
        //1、查询所有分类，返回list集合
        List<Category> allCategoryList = categoryMapper.findAll();

        //2、遍历所有分类的list集合，通过 parentId = 0得到所有一级分类
        List<Category> oneCategoryList = allCategoryList.stream().filter(item -> item.getParentId().longValue() == 0).toList();

        //3、遍历所有一级分类list集合，条件判断：id = parentId，得到一级下面的二级分类
        oneCategoryList.stream().forEach(oneCategory -> {
            List<Category> twoCategoryList = allCategoryList.stream().filter(item -> Objects.equals(item.getParentId(), oneCategory.getId())).toList();
            //把二级分类封装到一级分类里面
            oneCategory.setChildren(twoCategoryList);

            //4、遍历所有二级分类，条件判断：id = parentId，得到二级下面的三级分类
            twoCategoryList.stream().forEach(twoCategory -> {
                List<Category> threeCategoryList = allCategoryList.stream().filter(item -> Objects.equals(item.getParentId(), twoCategory.getId())).toList();
                //把三级分类封装到二级分类里面
                twoCategory.setChildren(threeCategoryList);
            });
        });

        return oneCategoryList;
    }
}
