package com.fuswx.spzx.product.controller;

import com.fuswx.spzx.model.dto.h5.ProductSkuDto;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.h5.ProductItemVo;
import com.fuswx.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/product")
@Tag(name = "产品管理")
public class ProductController {

    @Resource
    private ProductService productService;

    @Operation(summary = "分页查询", description = "分页查询接口")
    @GetMapping(value = "/{page}/{limit}")
    public Result findByPage(@Parameter(name = "page", description = "当前页码", required = true) @PathVariable("page") Integer page,
                             @Parameter(name = "limit", description = "每页记录值", required = true) @PathVariable("limit") Integer limit,
                             @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false)ProductSkuDto productSkuDto){
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品详情", description = "商品详情接口")
    @GetMapping(value = "/item/{skuId}")
    public Result item(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Integer skuId){
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

    //远程调用：根据skuId返回sku信息
    @Operation(summary = "根据skuId返回sku信息", description = "根据skuId返回sku信息")
    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable("skuId") Integer skuId){
        return productService.getBySkuId(skuId);
    }
}
