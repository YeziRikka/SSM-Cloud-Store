package com.itWk.admin.controller;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码工具控制类
 */
@Controller
@RequestMapping //在配置文件中写好了访问地址，不需要在这里进行额外的配置
public class CaptChaController {
    /**
     * 验证码工具
     */
    @RequestMapping("/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 自动生成验证码图片
         * 将验证码图片存储到session中， key = captcha 默认4个字母
         */
        CaptchaUtil.out(request, response);
    }
}
