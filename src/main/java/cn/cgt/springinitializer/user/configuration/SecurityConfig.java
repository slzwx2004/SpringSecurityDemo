package cn.cgt.springinitializer.user.configuration;

import cn.cgt.springinitializer.user.handler.MyAuthenticationAccessDeniedHandler;
import cn.cgt.springinitializer.user.handler.MyAuthenticationFailureHandler;
import cn.cgt.springinitializer.user.handler.MyAuthenticationSucessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security配置类
 */
@Configuration
/**
 * @EnableWebSecurity是开启SpringSecurity的默认行为
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private MyAuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;


    /**
     * 密码明文加密方式配置，通过这样注入，可以统一使用一个加密对象
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * oauth2登录
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.oauth2Login();
//
//        http.authorizeRequests()
//                .anyRequest().authenticated();
//    }

    /**
     * 表单登录
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 表单方式
                .loginPage("/authentication/require")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSucessHandler) // 处理登录成功
                .failureHandler(authenticationFailureHandler) // 处理登录失败
                .and()
                .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler)//处理没权限
                .and()
                .authorizeRequests() // 授权配置
                .antMatchers("/authentication/require", "/login.html").permitAll()
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
                .and().csrf().disable();
    }


}

