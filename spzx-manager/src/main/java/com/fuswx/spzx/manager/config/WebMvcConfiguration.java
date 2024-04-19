package com.fuswx.spzx.manager.config;

import com.fuswx.spzx.manager.intercepor.LoginAuthInterceptor;
import com.fuswx.spzx.manager.properties.UserProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;

    @Resource
    private UserProperties userProperties;

    //拦截器注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
//                .excludePathPatterns("/admin/system/index/generateValidateCode", "/admin/system/index/login")
                .excludePathPatterns(userProperties.getNoAuthUrls())
                .addPathPatterns("/**");
    }

    //跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 添加路径规则
                .allowCredentials(true)  // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")  // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
