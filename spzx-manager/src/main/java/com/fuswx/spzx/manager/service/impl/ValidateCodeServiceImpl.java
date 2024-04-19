package com.fuswx.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.fuswx.spzx.manager.service.ValidateCodeService;
import com.fuswx.spzx.model.vo.system.ValidateCodeVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //生成图片验证码
    @Override
    public ValidateCodeVo generateValidateCode() {
        //1、通过工具生成图片验证码
        //hutool
        //int width 宽度, int height 高度, int codeCount 验证码位数, int circleCount 干扰线的数量
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String codeValue = circleCaptcha.getCode(); //4位验证码值
        String imageBase64 = circleCaptcha.getImageBase64(); //返回图片验证码，base64编码

        //2、把验证码存储到redis里面，设置redis的key：uuid、redis的value：验证码的值
        //设置过期时间
        String key = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:validate" + key, codeValue, 5, TimeUnit.MINUTES);

        //3、返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);  //redis存储数据key
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
