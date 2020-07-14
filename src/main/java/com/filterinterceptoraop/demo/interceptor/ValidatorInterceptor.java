package com.filterinterceptoraop.demo.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/*
*
* interceptor for api sign
*
* */
@Component
public class ValidatorInterceptor implements HandlerInterceptor {

    /*
    *@author:liuhongdi
    *@date:2020/7/1 下午4:00
    *@description:检查通用的变量是否存在，是否合法
     * @param request：请求对象
     * @param response：响应对象
     * @param handler：处理对象：controller中的信息   *
     * *@return:true表示正常,false表示被拦截
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("---------------interceptor begin");

        //打印request参数
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                System.out.println("interceptor parameters: name:"+en+";value:"+value);
            }
        }

        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取处理当前请求的 handler 信息
            System.out.println("interceptor handler 类：" + handlerMethod.getBeanType().getName());
            System.out.println("interceptor handler 方法：" + handlerMethod.getMethod().getName());

            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            for (MethodParameter methodParameter : methodParameters) {
                // 只能获取参数的名称,type,index，不能获取到参数的值
                System.out.println("interceptor parameter Name: " + methodParameter.getParameterName());
                System.out.println("interceptor parameter Type: " + methodParameter.getParameterType());
                System.out.println("interceptor parameter Index: " + methodParameter.getParameterIndex());
            }
        }
        //sign校验无问题,放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}