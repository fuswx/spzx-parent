package com.fuswx.spzx.user.controller;

import com.fuswx.spzx.model.dto.h5.UserLoginDto;
import com.fuswx.spzx.model.dto.h5.UserRegisterDto;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.h5.UserInfoVo;
import com.fuswx.spzx.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "会员用户接口")
@RequestMapping("/api/user/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Operation(summary = "会员注册", description = "会员注册接口")
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "会员登录", description = "会员登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto){
        String token = userInfoService.login(userLoginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息接口")
    @GetMapping("/auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }
}
