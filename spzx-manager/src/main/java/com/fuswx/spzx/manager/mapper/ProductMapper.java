package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.dto.product.ProductDto;
import com.fuswx.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    //列表（条件分页查询）
    List<Product> findByPage(ProductDto productDto);

    //保存
    void save(Product product);

    //根据id查询商品基本信息 product表
    Product findProductById(Integer id);

    //修改product
    void updateById(Product product);

    //根据商品id删除product表
    void deleteById(Integer id);
}
