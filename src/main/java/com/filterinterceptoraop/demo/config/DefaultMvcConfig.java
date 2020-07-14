package com.filterinterceptoraop.demo.config;


import com.filterinterceptoraop.demo.filter.ParamFilter;
import com.filterinterceptoraop.demo.interceptor.ValidatorInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @desc: mvc config
 * @author: liuhongdi
 * @date: 2020-07-01 11:40
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DefaultMvcConfig implements WebMvcConfigurer {

    @Resource
    private ValidatorInterceptor validatorInterceptor;

    //add controller path
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/home/home");
    }

    //添加Interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validatorInterceptor)
                .addPathPatterns("/home/home**")
                .excludePathPatterns("/html/*","/js/*");
    }
    //add filter
    @Bean
    public FilterRegistrationBean addFilterBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ParamFilter());
        registration.setName("paramFilter");
        registration.setOrder(1);  //请求中过滤器执行的先后顺序，值越小越先执行
        registration.addUrlPatterns("/*");
        return registration;
    }
}