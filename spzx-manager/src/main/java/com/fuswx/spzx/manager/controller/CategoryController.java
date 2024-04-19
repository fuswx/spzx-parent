package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.CategoryService;
import com.fuswx.spzx.model.entity.product.Category;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/category")
@Tag(name = "类别接口", description = "有关于类别的接口")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    //分类列表，每次查询一层数据
    //SELECT * FROM category where parent_id = id
    @GetMapping("/findCategoryList/{id}")
    @Operation(summary = "分类列表，每次查询一层数据接口", description = "分类列表，每次查询一层数据接口")
    public Result findCategoryList(@PathVariable Integer id){
        List<Category> list = categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //导出
    @GetMapping("/exportData")
    @Operation(summary = "导出接口", description = "导出接口")
    public void exportData(HttpServletResponse response){
        categoryService.exportData(response);
    }

    //导入
    @PostMapping("/importData")
    @Operation(summary = "导入接口", description = "导入接口")
    public Result importData(MultipartFile file) {
        //获取上传文件
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
