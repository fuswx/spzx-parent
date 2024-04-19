package com.fuswx.spzx.manager.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.fuswx.spzx.manager.service.SysUserService;
import com.fuswx.spzx.manager.service.ValidateCodeService;
import com.fuswx.spzx.model.dto.system.LoginDto;
import com.fuswx.spzx.model.entity.system.SysUser;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.system.LoginVo;
import com.fuswx.spzx.model.vo.system.ValidateCodeVo;
import com.fuswx.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ValidateCodeService validateCodeService;

    //用户退出
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //获取当前用户登录信息
//    @GetMapping(value = "/getUserInfo")
////    public Result getUserInfo(HttpServerRequest request){
//    public Result getUserInfo(@RequestHeader(name = "token")String token){
//        //1、从请求头获取token
////        String token = request.getHeader("token");
//
//        //2、根据token查询redis获取用户信息
//        SysUser sysUser = sysUserService.getUserInfo(token);
//
//        //3、用户信息返回
//        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
//    }
    @GetMapping(value = "/getUserInfo")
//    public Result getUserInfo(HttpServerRequest request){
    public Result getUserInfo(){
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
    }

    //生成图片验证码
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    //用户登录
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
}
