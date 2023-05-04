package com.example.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.pojo.Permission;
import com.example.pojo.Role;
import com.example.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @Date 2022/10/10 7:27 下午
 * @Description 用户权限验证
 * @Author Sxy
 */

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference      //从zookeeper注册中心查找服务
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) { //用户名不存在
            return null;
        }
        //动态为当前用户授权
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            //遍历角色集合，为用户授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            //遍历权限集合，为用户授权
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
    }
}
