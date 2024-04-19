package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.ProductSpecService;
import com.fuswx.spzx.model.entity.base.ProductUnit;
import com.fuswx.spzx.model.entity.product.ProductSpec;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/productSpec")
@Tag(name = "商品规格有关接口", description = "有关于商品规格有关的接口")
public class ProductSpecController {

    @Resource
    private ProductSpecService productSpecService;

    //产品列表
    @GetMapping("/{page}/{limit}")
    @Operation(summary = "商品规格列表接口", description = "商品规格列表接口")
    public Result list(@PathVariable(value = "page") Integer page, @PathVariable(value = "limit") Integer limit){
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //产品添加
    @PutMapping("/save")
    @Operation(summary = "商品规格添加接口", description = "商品规格添加接口")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //产品修改
    @PutMapping("/updateById")
    @Operation(summary = "商品规格修改接口", description = "商品规格修改接口")
    public Result updateById(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //产品删除
    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "商品规格删除接口", description = "商品规格删除接口")
    public Result deleteById(@PathVariable(value = "id") Integer id){
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查找所有商品规格
    @GetMapping("/findAll")
    @Operation(summary = "查找所有商品规格", description = "查找所有商品规格接口")
    public Result findAll() {
        List<ProductUnit> productUnitList = productSpecService.findAll();
        return Result.build(productUnitList, ResultCodeEnum.SUCCESS);
    }

}
