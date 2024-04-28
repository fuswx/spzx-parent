package com.fuswx.spzx.user.controller;

import com.fuswx.spzx.model.entity.user.UserAddress;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {

    @Resource
    private UserAddressService userAddressService;

    @Operation(summary = "获取用户地址列表", description = "获取用户地址列表")
    @GetMapping("/auth/findUserAddressList")
    public Result findUserAddressList() {
        List<UserAddress> list = userAddressService.findUserAddressList();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据id获取收货地址信息", description = "根据id获取收货地址信息接口")
    @GetMapping("/getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable("id") Integer id) {
        return userAddressService.getUserAddress(id);
    }
}
