package com.fuswx.spzx.product.controller;

import com.fuswx.spzx.model.entity.product.Category;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value = "/api/product/category")
//@CrossOrigin  //跨域
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    //查询所有分类，树形封装
    @Operation(summary = "获取分类树形数据", description = "获取分类树形数据")
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree(){
        List<Category> list = categoryService.findCategoryTree();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
