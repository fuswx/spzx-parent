package com.fuswx.spzx.user.service.impl;

import com.fuswx.spzx.model.entity.user.UserAddress;
import com.fuswx.spzx.user.mapper.UserAddressMapper;
import com.fuswx.spzx.user.service.UserAddressService;
import com.fuswx.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;

    //获取用户地址列表
    @Override
    public List<UserAddress> findUserAddressList() {
        Integer userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findUserAddressList(userId);
    }

    //根据id获取收货地址信息
    @Override
    public UserAddress getUserAddress(Integer id) {
        return userAddressMapper.getUserAddress(id);
    }
}
