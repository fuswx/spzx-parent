package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductUnitMapper {

    //findAll
    List<ProductUnit> findAll();
}
