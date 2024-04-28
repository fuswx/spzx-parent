package com.fuswx.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fuswx.spzx.common.exception.FuswxException;
import com.fuswx.spzx.manager.listener.ExcelListener;
import com.fuswx.spzx.manager.mapper.CategoryMapper;
import com.fuswx.spzx.manager.service.CategoryService;
import com.fuswx.spzx.model.entity.product.Category;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.product.CategoryExcelVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategoryList(Integer id) {
        //1、根据id条件值进行查询，返回list集合
        // SELECT * FROM category where parent_id = id
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);

        //2、遍历返回list集合
        // 判断每个分类是否有下一层分类，如果有设置 hasChildren = true
        if (!CollectionUtils.isEmpty(categoryList)){
            categoryList.stream().forEach(category -> {
                //判断每个分类是否有下一层分类
                // SELECT count(*) FROM category where parent_id = id
                int count = categoryMapper.selectCountByParentId(category.getId());
                //有下一层分类
                category.setHasChildren(count > 0);
            });
        }

        return categoryList;
    }

    //导出
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 1、设置响应头信息和其他信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //这里ULEncoder.encode可以防止中文乱码，当然和easyExcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");

            //设置响应头信息
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            //2、调用mapper方法查询所有分类，返回list集合
            List<Category> categoryList = categoryMapper.findAll();

            //List<Category> --> List<CategoryExcelVo>
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            categoryList.stream().forEach(category -> {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                //把category值获取出来，设置到categoryExcelVo里面
                BeanUtils.copyProperties(category, categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            });

            //3、调用EasyExcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVoList);
        }catch (Exception e){
            e.printStackTrace();
            throw new FuswxException(ResultCodeEnum.DATA_ERROR);
        }

    }

    @Override
    public void importData(MultipartFile file) {
        //监听器
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener)
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FuswxException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
