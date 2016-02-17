package com.platform.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.platform.common.config.ResultCode;

/**
 * <code>SessionTimeoutInterceptor</code>
 *
 * @author andy.zheng andy.zheng0807@gmail.com
 * @version 1.0, 2015/3/7 19:05
 * @since 3six5 1.0
 */
public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {

    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("The current request url: " + request.getRequestURL());
        Object obj = WebUtils.getSessionAttribute(request, "currentUser");
        if (null == obj) {
            ResultCode result = ResultCode.SESSION_TIME_OUT;
            Map<String, Object> resultData = HandleResultWrapper.getInstance().wrapped(1, result);
            response.getWriter().write(JSON.toJSONString(resultData));
            
/*            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.setHeader("sessionStatus", "timeout");
            } else {
                response.sendRedirect(request.getContextPath() + "/main.jsp");
            }*/
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
