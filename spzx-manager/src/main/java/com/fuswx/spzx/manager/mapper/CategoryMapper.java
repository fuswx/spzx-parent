package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.entity.product.Category;
import com.fuswx.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //1、根据id条件值进行查询，返回list集合
    List<Category> selectCategoryByParentId(Integer id);

    //判断每个分类是否有下一层分类
    int selectCountByParentId(Integer id);

    //调用mapper方法查询所有分类，返回list集合
    List<Category> findAll();

    //批量保存方法
    void batchInsert(List<CategoryExcelVo> categoryExcelVoList);
}
