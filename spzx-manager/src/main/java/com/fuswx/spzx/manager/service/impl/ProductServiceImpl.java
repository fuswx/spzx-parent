package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.manager.mapper.ProductDetailsMapper;
import com.fuswx.spzx.manager.mapper.ProductMapper;
import com.fuswx.spzx.manager.mapper.ProductSkuMapper;
import com.fuswx.spzx.manager.service.ProductService;
import com.fuswx.spzx.model.dto.product.ProductDto;
import com.fuswx.spzx.model.entity.product.Product;
import com.fuswx.spzx.model.entity.product.ProductDetails;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    private ProductDetailsMapper productDetailsMapper;

    //列表（条件分页查询）
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list = productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }

    //保存
    @Override
    public void save(Product product) {
        //1、先保存商品基本信息到product表
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);

        //2、获取商品sku列表集合，保存sku信息，product_sku表
        List<ProductSku> productSkuList = product.getProductSkuList();
        AtomicReference<Integer> index = new AtomicReference<>(0);
        productSkuList.stream().forEach(productSku -> {

            //商品编号
            productSku.setSkuCode(product.getId() + "_" + index);
            index.getAndSet(index.get() + 1);
            //商品ID
            productSku.setProductId(product.getId());
            //skuName
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            //设置销量
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            //保存数据
            productSkuMapper.save(productSku);
        });

        //3、保存商品详情数据到product_details表
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }

    //根据商品id查询商品信息
    @Override
    public Product getById(Integer id) {
        //1、根据id查询商品基本信息 product表
        Product product = productMapper.findProductById(id);

        //2、根据id查询商品sku信息列表 product_sku表
        List<ProductSku> productSkuList = productSkuMapper.findProductSkuByProductId(id);
        product.setProductSkuList(productSkuList);

        //3、根据商品id查询商品详情数据 product_details
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(id);
        String imageUrls = productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);

        return product;
    }

    //保存修改数据
    @Override
    public void update(Product product) {
        //修改product
        productMapper.updateById(product);

        //修改product_sku
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.stream().forEach(productSku -> productSkuMapper.updateById(productSku));

        //修改product_details
        String detailsImageUrls = product.getDetailsImageUrls();
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(product.getId());
        productDetails.setImageUrls(detailsImageUrls);
        productDetailsMapper.updateById(productDetails);
    }

    //删除
    @Override
    public void deleteById(Integer id) {
        //1、根据商品id删除product表
        productMapper.deleteById(id);

        //2、根据商品id删除product_sku表
        productSkuMapper.deleteByProductId(id);

        //3、根据商品id删除product_details表
        productDetailsMapper.deleteByProductId(id);
    }

    //审核
    @Override
    public void updateAuditStatus(Integer id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if (auditStatus == 1){
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        }else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批未通过");
        }
        productMapper.updateById(product);
    }

    //商品上下架
    @Override
    public void updateStatus(Integer id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if (status == 1){
            product.setStatus(1);
        }else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
