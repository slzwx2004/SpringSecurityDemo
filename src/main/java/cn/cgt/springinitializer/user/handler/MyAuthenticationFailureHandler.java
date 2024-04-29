package cn.cgt.springinitializer.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败逻辑，需要在WebSecurityConfigurerAdapter的子类中声明
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper mapper;

    /**
     *
     * @param request the request during which the authentication attempt occurred.
     * @param response the response.
     * @param exception 不同的失败原因对应不同的异常，比如用户名或密码错误对应的是BadCredentialsException，
     *                  用户不存在对应的是UsernameNotFoundException，用户被锁定对应的是LockedException等
     * request.
     * @throws IOException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        //我们需要在登录失败的时候返回失败信息,状态码定义为500（HttpStatus.INTERNAL_SERVER_ERROR.value()），即系统内部异常。
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(mapper.writeValueAsString(exception.getMessage()));
    }
}
