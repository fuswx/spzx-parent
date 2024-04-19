package com.fuswx.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.fuswx.spzx.model.dto.h5.ProductSkuDto;
import com.fuswx.spzx.model.entity.product.Product;
import com.fuswx.spzx.model.entity.product.ProductDetails;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.fuswx.spzx.model.vo.h5.ProductItemVo;
import com.fuswx.spzx.product.mapper.ProductDetailsMapper;
import com.fuswx.spzx.product.mapper.ProductMapper;
import com.fuswx.spzx.product.mapper.ProductSkuMapper;
import com.fuswx.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    private ProductDetailsMapper productDetailsMapper;

    //根据销量排序，获取前10条记录
    @Override
    public List<ProductSku> selectProductSkuBySale() {
        return productSkuMapper.selectProductSkuBySale();
    }

    //分页查询
    @Override
    @Cacheable(value = "product", key = "'pageAll'")
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(productSkuList);
    }

    //商品详情
    @Override
    public ProductItemVo item(Integer skuId) {
        //1、创建vo对象，用于封装最终数据
        ProductItemVo productItemVo = new ProductItemVo();

        //2、根据skuId获取sku信息
        ProductSku productSku = productSkuMapper.getById(skuId);

        //3、根据第二步获取到的sku，从sku获取productId，获取商品信息
        Integer productId = productSku.getProductId();
        Product product = productMapper.getById(productId);

        //4、productId获取商品详情信息
        ProductDetails productDetails = productDetailsMapper.getByProductId(productId);

        //5、封装map集合，商品规格对应商品skuId信息
        Map<String, Object> skuSpecValueMap = new HashMap<>();
        //根据商品id获取商品所有的sku列表
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productId);
        skuSpecValueMap = productSkuList.stream().collect(Collectors.toMap(ProductSku::getSkuSpec, ProductSku::getId));

        //6、把需要的数据封装到productItemVo里面
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        //封装详情图片
//        String imageUrls = productDetails.getImageUrls();
//        String[] split = imageUrls.split(",");
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));

        //封装轮播图list集合
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));

        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));

        return productItemVo;
    }

    //远程调用：根据skuId返回sku信息
    @Override
    public ProductSku getBySkuId(Integer skuId) {
        return productSkuMapper.getById(skuId);
    }
}
