package com.fuswx.spzx.product.controller;

import com.fuswx.spzx.model.entity.product.Brand;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product/brand")
@Tag(name = "品牌管理")
public class BrandController {
    @Resource
    private BrandService brandService;

    @Operation(summary = "获取全部品牌", description = "获取全部品牌接口")
    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
