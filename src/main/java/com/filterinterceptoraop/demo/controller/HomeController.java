package com.filterinterceptoraop.demo.controller;


import com.filterinterceptoraop.demo.annotation.RedisRateLimiter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/home")
    @ResponseBody
    @RedisRateLimiter(count = 3, time = 1)
    public String homeMethod(@RequestParam("v") String version,
                       HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       ModelMap modelMap) {
        System.out.println("---------------controller begin");
        System.out.println("v in controller:"+version);

        //打印request参数
        Enumeration<?> temp = httpServletRequest.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = httpServletRequest.getParameter(en);
                System.out.println("name:"+en+";value:"+value);
            }
        }

        return "this is home,v="+version;
    }


}
