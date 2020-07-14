package com.filterinterceptoraop.demo.wrapper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/*
*@author:liuhongdi
*@date:2020/7/14 上午11:50
*@description:自定义request wrapper,供修改request参数使用
*/
@SuppressWarnings("unchecked")
public class CustomRequestWrapper extends HttpServletRequestWrapper {

    private Map params;

    public CustomRequestWrapper(HttpServletRequest request, Map newParams) {
        super(request);
        this.params = newParams;
    }

    @Override
    public Map getParameterMap() {
         return params;
    }

    @Override
    public Enumeration getParameterNames() {
        Vector l = new Vector(params.keySet());
        return l.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            return (String[]) v;
        } else if (v instanceof String) {
            return new String[] { (String) v };
        } else {
             return new String[] { v.toString() };
        }
    }

     @Override
     public String getParameter(String name) {
         Object v = params.get(name);
         if (v == null) {
            return null;
         } else if (v instanceof String[]) {
             String[] strArr = (String[]) v;
             if (strArr.length > 0) {
                  return strArr[0];
             } else {
                 return null;
             }
         } else if (v instanceof String) {
             return (String) v;
         } else {
            return v.toString();
         }
     }

}




