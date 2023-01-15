package com.yiming.blog.aop;

import com.alibaba.fastjson.JSON;
import com.yiming.blog.utils.HttpContextUtils;
import com.yiming.blog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.yiming.blog.aop.LogAnnotation)")
    void pt() {}

    @Around("pt()")
    public Object Around(ProceedingJoinPoint point) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = point.proceed();
        long end = System.currentTimeMillis();
        record(point,end-begin);
        return result;

    }
    private void record(ProceedingJoinPoint point,long time){
        /*String methodName = point.getSignature().getName();
        String className = point.getSignature().getDeclaringTypeName();
        String methodDesc = point.getSignature().toString();
        log.info("{},{},{},{}",className,methodName,methodDesc,time);
*/
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        log.info("module:{}",logAnnotation.module());
        log.info("operation:{}",logAnnotation.operation());

        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}",className + "." + methodName + "()");

//        //请求的参数
        Object[] args = point.getArgs();
        String params = Arrays.toString(args);
//        String params = JSON.toJSONString(args);
        log.info("params:{}",params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));


        log.info("excute time : {} ms",time);
        log.info("=====================log end================================");

    }


}
