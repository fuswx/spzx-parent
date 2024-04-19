package com.fuswx.spzx.user.controller;

import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.user.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/sms")
@Tag(name = "管理短信验证码")
public class SmsController {

    @Resource
    private SmsService smsService;

    @GetMapping("/sendCode/{phone}")
    @Operation(summary = "发送短信验证码", description = "发送短信验证码接口")
    public Result sendValidateCode(@PathVariable("phone") String phone) {
        smsService.sendValidateCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
