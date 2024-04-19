package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.entity.base.ProductUnit;
import com.fuswx.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {

    //列表
    List<ProductSpec> findByPage();

    //添加
    void save(ProductSpec productSpec);

    //修改
    void update(ProductSpec productSpec);

    //删除
    void delete(Integer id);

    //查找所有商品规格
    List<ProductUnit> findAll();
}
