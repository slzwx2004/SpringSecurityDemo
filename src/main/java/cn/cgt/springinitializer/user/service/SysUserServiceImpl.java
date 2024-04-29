package cn.cgt.springinitializer.user.service;

import cn.cgt.springinitializer.user.entity.CustomUser;
import cn.cgt.springinitializer.user.entity.SysUser;
import cn.cgt.springinitializer.user.vo.LoginVo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {



    /**
     * 通过AuthenticationManager的authenticate方法来进行用户认证,
     */
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public void login(LoginVo loginVo) {
        // 将表单数据封装到 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
        // authenticate方法会调用loadUserByUsername
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 将返回的Authentication存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        // 校验成功，强转对象
//        CustomUser customUser = (CustomUser) authenticate.getPrincipal();
//        SysUser sysUser = customUser.getSysUser();
        //TODO  校验通过返回token
//        String token = JwtUtil.createToken(sysUser.getId(), sysUser.getUsername());
//        Map<String, Object> map = new HashMap<>();
//        map.put("token",token);
//        return Result.ok(map);
    }
}

