package com.fuswx.spzx.product.controller;

import com.fuswx.spzx.model.entity.product.Category;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.h5.IndexVo;
import com.fuswx.spzx.product.service.CategoryService;
import com.fuswx.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product")
//@CrossOrigin //跨域
public class IndexController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ProductService productService;

    @GetMapping("/index")
    @Operation(summary = "获取首页数据", description = "获取首页数据")
    public Result index(){
        //1、所有一级分类
        List<Category> categoryList = categoryService.selectOneCategory();

        //2、根据销量排序，获取前10条记录
        List<ProductSku> productSkuList = productService.selectProductSkuBySale();

        //3、封装数据到vo对象里面
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }

}
