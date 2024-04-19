package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.dto.product.ProductDto;
import com.fuswx.spzx.model.entity.product.Product;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageInfo;

public interface ProductService {

    //列表（条件分页查询）
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    //保存
    void save(Product product);

    //根据商品id查询商品信息
    Product getById(Integer id);

    //保存修改数据
    void update(Product product);

    //删除
    void deleteById(Integer id);

    //审核
    void updateAuditStatus(Integer id, Integer auditStatus);

    //商品上下架
    void updateStatus(Integer id, Integer status);

}
