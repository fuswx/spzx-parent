package com.fuswx.spzx.product.mapper;

import com.fuswx.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    //获取全部品牌
    List<Brand> findAll();
}
