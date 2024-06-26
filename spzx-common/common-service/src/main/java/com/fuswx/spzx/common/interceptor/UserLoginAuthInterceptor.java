package com.fuswx.spzx.common.interceptor;

import com.alibaba.fastjson2.JSON;
import com.fuswx.spzx.model.entity.user.UserInfo;
import com.fuswx.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userJson = redisTemplate.opsForValue().get("user:spzx:" + token);
        //放到threadLocal里面
        AuthContextUtil.setUserInfo(JSON.parseObject(userJson, UserInfo.class));
        return true;
    }
}
