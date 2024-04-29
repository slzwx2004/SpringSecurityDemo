package cn.cgt.springinitializer.user.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功逻辑，需要在WebSecurityConfigurerAdapter的子类中声明
 */
@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //登录成功后页面将输出登录用户信息
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(authentication.getPrincipal().toString());
        //登录成功后页面将跳转回引发跳转的页面
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null){
            redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        }else {
            //如果登录前的页面是空白，则跳转到指定页面，比如跳转到/index，可以将savedRequest.getRedirectUrl()修改为/index：
            redirectStrategy.sendRedirect(request, response, "/index");
        }
    }
}
