package com.cumt.onlineexam.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getSession().getAttribute("student")==null&&request.getSession().getAttribute("teacher")==null&&request.getSession().getAttribute("admin")==null){
            response.sendRedirect("/index");
            return false;
        }
        return true;
    }
}
