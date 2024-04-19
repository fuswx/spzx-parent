package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.ProductService;
import com.fuswx.spzx.model.dto.product.ProductDto;
import com.fuswx.spzx.model.entity.product.Product;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/product/product")
@Tag(name = "商品接口", description = "商品接口")
public class ProductController {

    @Resource
    private ProductService productService;

    //列表（条件分页查询）
    @GetMapping("/{page}/{limit}")
    @Operation(summary = "列表（条件分页查询）", description = "列表（条件分页查询）")
    public Result list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, ProductDto productDto){
        PageInfo<Product> pageInfo = productService.findByPage(page, limit, productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @PostMapping("/save")
    @Operation(summary = "保存商品", description = "保存商品接口")
    public Result save(@RequestBody Product product){
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //根据商品id查询商品信息
    @GetMapping("/getById/{id}")
    @Operation(summary = "根据商品id查询商品信息", description = "根据商品id查询商品信息")
    public Result getById(@PathVariable("id") Integer id){
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    //保存修改数据
    @PutMapping("/updateById")
    @Operation(summary = "保存修改数据", description = "保存修改数据接口")
    public Result update(@RequestBody Product product){
        productService.update(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //删除
    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "删除商品", description = "删除商品接口")
    public Result deleteById(@Parameter(name = "id", description = "商品id", required = true) @PathVariable("id") Integer id){
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //商品审核
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    @Operation(summary = "商品审核", description = "商品审核接口")
    public Result updateAuditStatus(@PathVariable("id") Integer id, @PathVariable("auditStatus") Integer auditStatus){
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //商品上下架
    @GetMapping("/updateStatus/{id}/{status}")
    @Operation(summary = "商品上下架", description = "商品上下架接口")
    public Result updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status){
        productService.updateStatus(id, status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
