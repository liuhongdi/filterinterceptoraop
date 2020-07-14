package com.filterinterceptoraop.demo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filterinterceptoraop.demo.annotation.RedisRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Component
@Aspect
public class RedisRateLimiterAspect {
    @Pointcut("@annotation(com.filterinterceptoraop.demo.annotation.RedisRateLimiter)")
    private void pointcut() {}

    /*
    *   around,
    *   if reach limit in time
    *   return error info
    * */
    @Around(value = "pointcut()")
    public Object requestLimit(ProceedingJoinPoint joinPoint) throws Exception {
        System.out.println("---------------aop begin");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //打印request参数
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                System.out.println("aop request parameter name:"+en+";value:"+value);
            }
        }

        Object[] args = joinPoint.getArgs();
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            //获取目标方法
            Method targetMethod = methodSignature.getMethod();
            String method_name = targetMethod.getName();
            System.out.println("aop method name:"+method_name);
            String[] paramNames = methodSignature.getParameterNames();

            Map<String, Object> nameAndArgs = new HashMap<String, Object>();
            for (int i = 0; i < paramNames.length; i++) {
                nameAndArgs.put(paramNames[i], args[i]);// paramNames即参数名
                if (paramNames[i].equals("version")) {
                    System.out.println("version value:"+args[i]);
                    args[i] = "aopv";
                }
                System.out.println("aop method parameter name:"+paramNames[i]+";value:"+args[i]);
            }

            if (targetMethod.isAnnotationPresent(RedisRateLimiter.class)) {
                return joinPoint.proceed(args);
            } else {
                return joinPoint.proceed();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}