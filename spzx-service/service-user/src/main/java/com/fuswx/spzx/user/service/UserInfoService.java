package com.fuswx.spzx.user.service;

import com.fuswx.spzx.model.dto.h5.UserLoginDto;
import com.fuswx.spzx.model.dto.h5.UserRegisterDto;
import com.fuswx.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    //会员注册
    void register(UserRegisterDto userRegisterDto);

    //登录
    String login(UserLoginDto userLoginDto);

    //获取当前登录用户信息
    UserInfoVo getCurrentUserInfo(String token);
}
