package com.fuswx.spzx.order.service.impl;

import com.fuswx.spzx.common.exception.FuswxException;
import com.fuswx.spzx.feign.cart.CartFeignClient;
import com.fuswx.spzx.feign.product.ProductFeignClient;
import com.fuswx.spzx.feign.user.UserFeignClient;
import com.fuswx.spzx.model.dto.h5.OrderInfoDto;
import com.fuswx.spzx.model.entity.h5.CartInfo;
import com.fuswx.spzx.model.entity.order.OrderInfo;
import com.fuswx.spzx.model.entity.order.OrderItem;
import com.fuswx.spzx.model.entity.order.OrderLog;
import com.fuswx.spzx.model.entity.product.ProductSku;
import com.fuswx.spzx.model.entity.user.UserAddress;
import com.fuswx.spzx.model.entity.user.UserInfo;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.h5.TradeVo;
import com.fuswx.spzx.order.mapper.OrderInfoMapper;
import com.fuswx.spzx.order.mapper.OrderItemMapper;
import com.fuswx.spzx.order.mapper.OrderLogMapper;
import com.fuswx.spzx.order.service.OrderInfoService;
import com.fuswx.spzx.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private CartFeignClient cartFeignClient;

    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderLogMapper orderLogMapper;

    //结算
    @Override
    public TradeVo getTrade() {
        //远程调用获取购物车选中商品列表
        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        //创建list集合用于封装订单项
        List<OrderItem> orderItemList = cartInfoList.stream().map(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            return orderItem;
        }).collect(Collectors.toList());

        //获取订单支付总金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }

        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }

    //提交订单
    @Override
    public Integer submitOrder(OrderInfoDto orderInfoDto) {
        //1、orderInfoDto获取所有订单项list List<OrderItem>
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();

        //2、判断 List<OrderItem> 为空，抛出异常
        if (CollectionUtils.isEmpty(orderItemList)){
            throw new FuswxException(ResultCodeEnum.DATA_ERROR);
        }

        //3、校验商品库存是否充足
        //遍历 List<OrderItem> 集合，得到每个 orderItem
        //根据skuId获取sku信息，远程调用获取商品sku信息，（包含库存量）
        //远程调用获取商品sku信息，（包含库存量）
        orderItemList.stream().forEach(orderItem -> {
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if (productSku == null){
                throw new FuswxException(ResultCodeEnum.DATA_ERROR);
            }
            //校验每个 orderItem 库存量是否充足
            if (orderItem.getSkuNum() > productSku.getStockNum()){
                throw new FuswxException(ResultCodeEnum.STOCK_LESS);
            }
        });

        //4、添加数据到order_info表
        //封装数据到 OrderInfo 对象
        OrderInfo orderInfo = new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        orderInfo.setUserId(userInfo.getId());
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        orderInfo.setNickName(userInfo.getNickName());

        //封装收货地址信息
        Integer userAddressId = orderInfoDto.getUserAddressId();
        //远程调用：根据收货地址id，获取用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(userAddressId);
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);

        orderInfoMapper.save(orderInfo);

        //5、添加数据到order_item表
        //添加 List<OrderItem> 里面数据，把集合每个 orderItem 添加到表
        orderItemList.stream().forEach(orderItem -> {
            //设置对应的订单id
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        });

        //6、添加数据到order_log表
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        //7、远程调用：把生成订单商品，从购物车删除
        cartFeignClient.deleteChecked();

        //8、返回订单id
        return orderInfo.getId();
    }

    //获取订单信息
    @Override
    public OrderInfo getOrderInfo(Integer orderId) {
        return orderInfoMapper.getById(orderId);
    }

    //立即购买
    @Override
    public TradeVo buy(Integer skuId) {
        //封装订单项集合
        List<OrderItem> orderItemList = new ArrayList<>();
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(productSku.getSalePrice());
        return tradeVo;
    }

    //获取订单分页列表
    @Override
    public PageInfo<OrderInfo> findOrderByPage(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page, limit);
        //查询订单信息
        Integer userId = AuthContextUtil.getUserInfo().getId();
        List<OrderInfo> orderInfoList = orderInfoMapper.findUserPage(userId, orderStatus);

        //订单里面所有订单项
        return new PageInfo<>(orderInfoList.stream().map(orderInfo -> {
            //订单id查询订单里面订单项
            List<OrderItem> orderItemList = orderItemMapper.findByOrderId(orderInfo.getId());
            //封装
            orderInfo.setOrderItemList(orderItemList);
            return orderInfo;
        }).collect(Collectors.toList()));
    }

    //远程调用：根据订单编号获取订单信息
    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderNo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.findByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }

    //更新订单支付状态
    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {
        //更新订单状态
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderNo(orderNo);
        orderInfo.setOrderStatus(1);
        orderInfo.setPayType(orderStatus);
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.save(orderLog);

    }
}
