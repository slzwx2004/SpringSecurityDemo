package cn.cgt.springinitializer.user.service;

import cn.cgt.springinitializer.user.entity.CustomUser;
import cn.cgt.springinitializer.user.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: SpringBoot
 * @ClassName service
 * @Author: liutao
 * @Description: 用户业务接口
 * @Create: 2023-06-11 17:43
 * @Version 1.0
 **/

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名，查询用户对象
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 认证
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        //框架会根据返回的sysUser对象的密码，与用户输入的密码进行校对，以校验密码是否正确，这里写死了123456是密码
        sysUser.setPassword(passwordEncoder.encode("123456"));
        sysUser.setId(1);
        System.out.println(sysUser.getPassword());
        //TODO 授权
        List<String> userPermsList = new ArrayList<>();
        if("admin".equals(username)){
            userPermsList.add("user_info");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        CustomUser loginUser = new CustomUser(sysUser, authorities);
        if(null == loginUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        if(!loginUser.isEnabled()) {
            throw new RuntimeException("账号已停用");
        }
        return loginUser;
    }


}
