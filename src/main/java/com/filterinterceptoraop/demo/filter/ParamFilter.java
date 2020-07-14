package com.filterinterceptoraop.demo.filter;


import com.filterinterceptoraop.demo.wrapper.CustomRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------------filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("----------------filter doFilter begin");
        //打印得到的request参数
        Enumeration paramNames = servletRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = servletRequest.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    System.out.println("filter parameter name:"+paramName+";value:"+paramValue);
                }
            }
        }

        HashMap m = new HashMap(servletRequest.getParameterMap());
        m.put("newp", new String[] { "abcd" });
        m.put("v", new String[] { "filterv" });
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        CustomRequestWrapper wrapRequest = new CustomRequestWrapper(req, m);
        servletRequest = wrapRequest;
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("----------------filter destroy");
    }
}