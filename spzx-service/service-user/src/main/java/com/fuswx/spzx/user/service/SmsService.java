package com.fuswx.spzx.user.service;

public interface SmsService {
    //发送短信验证码
    void sendValidateCode(String phone);
}
