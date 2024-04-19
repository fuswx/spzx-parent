package com.fuswx.spzx.common.log.aspect;

import com.fuswx.spzx.common.log.annotation.Log;
import com.fuswx.spzx.common.log.service.AsyncOperLogService;
import com.fuswx.spzx.common.log.utils.LogUtil;
import com.fuswx.spzx.model.entity.system.SysOperLog;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect { //环绕通知切面类定义

    @Resource
    private AsyncOperLogService asyncOperLogService;

    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){

//        String title = sysLog.title();
//        int i = sysLog.businessType();
//        System.out.println("title: " + title + "::businessType: " + i);

        //业务方法调用之前，封装数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandlerLog(sysLog, joinPoint, sysOperLog);

        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();  //执行业务方法

//            System.out.println("在业务方法之后执行");
            //调用业务方法之后，封装数据
            LogUtil.afterHandlerLog(sysLog, proceed, sysOperLog, 0, null);
        } catch (Throwable e) {  //代码执行进入到catch中，业务方法执行产生异常
            e.printStackTrace();
            LogUtil.afterHandlerLog(sysLog, proceed, sysOperLog, 1, e.getMessage());
            //抛出运行时异常
            throw new RuntimeException(e);
        }
        //调用service方法把日志信息添加到数据库里面
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed;  //返回执行结果
    }
}
