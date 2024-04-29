package cn.cgt.springinitializer.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Program: SpringBoot
 * @ClassName User
 * @Author: liutao
 * @Description: 用户类
 * @Create: 2023-06-11 17:22
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("性别")
    private int sex;
    @ApiModelProperty("年龄")
    private int age;


}
