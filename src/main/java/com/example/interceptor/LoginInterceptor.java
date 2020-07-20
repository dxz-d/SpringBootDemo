package com.example.interceptor;

import com.example.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 登录拦截器
 * @author diaoxiuze
 * @date 2020/6/28 15:24
 */
public class LoginInterceptor implements HandlerInterceptor  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null ||user.getId()<=0) {
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        } else {
            return true;
        }
    }


}
























