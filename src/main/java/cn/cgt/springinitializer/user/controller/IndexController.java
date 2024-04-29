package cn.cgt.springinitializer.user.controller;

import cn.cgt.springinitializer.user.entity.CustomUser;
import cn.cgt.springinitializer.user.service.SysUserService;
import cn.cgt.springinitializer.user.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Api(tags = "系统管理-登录管理")
@RequestMapping("/")
@RestController
public class IndexController {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/test")
    public CustomUser test(){
        System.out.println("Test Result~~~~");
        return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 处理登录逻辑
     * @param loginVo
     * @return
     */
    @ApiOperation("登录接口")
    @PostMapping("login")
    public String login(@RequestBody LoginVo loginVo){
        System.out.println(111);
        sysUserService.login(loginVo);
        return "登录成功";
    }


    /**
     * 在未登录的情况下，当用户访问html资源的时候跳转到登录页，否则返回JSON格式数据，状态码为401。
     * 作为项目的总入口
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html"))
                redirectStrategy.sendRedirect(request, response, "/login.html");
        }
        return "访问的资源需要身份认证！";
    }

    /**
     * 登录后的首页，由MyAuthenticationSucessHandler.onAuthenticationSuccess 指定跳跃位置
     * @return 用户的认证信息
     */
    @GetMapping("index")
    @PreAuthorize("hasAuthority('user_info')")
    @ApiOperation("用户信息")
    public Object index() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("hello")
    public String hello() {
        return "hello spring security";
    }



}

