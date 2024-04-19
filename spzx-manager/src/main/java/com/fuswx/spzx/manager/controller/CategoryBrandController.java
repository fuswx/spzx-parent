package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.CategoryBrandService;
import com.fuswx.spzx.model.dto.product.CategoryBrandDto;
import com.fuswx.spzx.model.entity.product.Brand;
import com.fuswx.spzx.model.entity.product.CategoryBrand;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
@Tag(name = "系统类别接口", description = "有关于系统类别的接口")
public class CategoryBrandController {

    @Resource
    private CategoryBrandService categoryBrandService;

    //分类品牌条件分页查询
    @GetMapping("/{page}/{limit}")
    @Operation(summary = "分类品牌条件分页查询接口", description = "用于分类品牌条件分页查询的接口")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer limit, CategoryBrandDto categoryBrandDto){
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @PostMapping("/save")
    @Operation(summary = "添加分类品牌接口", description = "用于添加分类品牌的接口")
    public Result save(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //根据分类id查询对应品牌数据
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    @Operation(summary = "根据分类id查询对应品牌数据", description = "根据分类id查询对应品牌数据")
    public Result findBrandByCategoryId(@PathVariable("categoryId") Integer categoryId){
        List<Brand> list = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
