package com.itWk.admin.inteceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录保护拦截器
 * 检查session中是否有数据
 * 有：放行 没有：跳转登录界面
 */
public class LoginProtectInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断session中是否有数据
        Object userInfo = request.getSession().getAttribute("userInfo");
        if (userInfo != null) {
            //不为空，放行
            return true;
        }else {
            //为空，拦截，重定向返回到登录页面
            response.sendRedirect(request.getContextPath() + "index.html");
            return false;
        }
    }
}
