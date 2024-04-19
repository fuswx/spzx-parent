package com.fuswx.spzx.user.service.impl;

import com.fuswx.spzx.user.service.SmsService;
import com.fuswx.spzx.utils.HttpUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //发送短信验证码
    @Override
    public void sendValidateCode(String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (StringUtils.hasText(code)){
            return;
        }
        //1、生成验证码
        code = RandomStringUtils.randomNumeric(4);

        //2、把生成的验证码放到redis里面，设置过期时间
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

        //3、向手机号发送短信验证码
        sendMessage(phone, code);
    }

    //发送短信验证码的方法
    private void sendMessage(String phone, String code) {
        String host = "https://dfsmsv2.market.alicloudapi.com";
        String path = "/data/send_sms_v2";
        String method = "POST";
        String appcode = "4449a54b0ba94833bcd06e042f662cf1";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        bodys.put("content", "code:" + code);
        bodys.put("template_id", "TPL_0000");
        bodys.put("phone_number", phone);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
