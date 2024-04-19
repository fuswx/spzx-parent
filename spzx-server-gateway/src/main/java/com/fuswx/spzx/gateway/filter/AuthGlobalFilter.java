package com.fuswx.spzx.gateway.filter;

import cn.hutool.core.text.AntPathMatcher;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fuswx.spzx.model.entity.user.UserInfo;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //判断当前路径 /api/**/auth/**，登录校验
        if (antPathMatcher.match("/auth/**", path)) {
            //登录校验
            UserInfo userInfo = this.getUserInfo(request);
            if (userInfo == null){
                return out(exchange.getResponse(), ResultCodeEnum.LOGIN_AUTH);
            }
        }

        //放行
        return chain.filter(exchange);
    }

    private UserInfo getUserInfo(ServerHttpRequest request) {
        //从请求头获取token
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if (tokenList != null){
            token = tokenList.get(0);
        }
        //判断token是否为空
        if (!StringUtils.hasText(token)){
            //根据token查询redis
            String userJson = redisTemplate.opsForValue().get("user:spzx:" + token);
            if (!StringUtils.hasText(userJson)){
                return JSON.parseObject(userJson, UserInfo.class);
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum){
        Result result = Result.build(null, resultCodeEnum);
        byte[] bytes = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
        return response.writeWith(Mono.just(buffer));
    }
}
