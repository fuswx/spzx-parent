package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.ProductUnitService;
import com.fuswx.spzx.model.entity.base.ProductUnit;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productUnit")
@Tag(name = "商品单元数据", description = "有关商品单元数据接口")
public class ProductUnitController {

    @Resource
    private ProductUnitService productUnitService;

    @GetMapping("/findAll")
    @Operation(summary = "查找所有", description = "查找所有接口")
    public Result findAll(){
        List<ProductUnit> list = productUnitService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
