package com.loiterer.listener.common.handler;

import com.loiterer.listener.common.exception.ListenerException;
import com.loiterer.listener.common.result.ResultCodeEnum;
import com.loiterer.listener.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 *
 * @author cmt
 * @date 2020/10/21
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token");
        try {
            jwtUtil.verify(token);
            return true;
        } catch (Exception e) {
            throw new ListenerException(ResultCodeEnum.FAIL.getCode(), "验证token失败");
        }
    }
}
