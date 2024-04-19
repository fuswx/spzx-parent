package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.common.log.annotation.Log;
import com.fuswx.spzx.common.log.enums.OperatorType;
import com.fuswx.spzx.manager.service.BrandService;
import com.fuswx.spzx.model.entity.product.Brand;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/brand")
@Tag(name = "系统接口", description = "有关于系统功能的接口")
public class BrandController {

    @Resource
    private BrandService brandService;

    //查询所有品牌
    @GetMapping("/findAll")
    @Operation(summary = "查询所有品牌接口", description = "用于查询所有品牌的接口")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //列表
    @GetMapping("/{page}/{limit}")
    @Operation(summary = "查询所有品牌列表接口", description = "用于查询所有品牌列表的接口")
    @Log(title = "品牌管理:列表", businessType = 0, operatorType = OperatorType.OTHER)
    public Result list(@PathVariable Integer page, @PathVariable Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @PostMapping("/save")
    @Operation(summary = "添加品牌接口", description = "用于添加品牌的接口")
    public Result save(@RequestBody Brand brand){
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //修改和删除
    @PutMapping("/update")
    @Operation(summary = "修改品牌接口", description = "用于修改品牌的接口")
    public Result update(@RequestBody Brand brand){
        brandService.update(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById")
    @Operation(summary = "删除品牌接口", description = "用于删除品牌的接口")
    public Result deleteById(Integer id){
        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
