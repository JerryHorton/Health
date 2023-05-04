package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.PermissionDao;
import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.pojo.Permission;
import com.example.pojo.Role;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @version 1.0
 * @Date 2022/10/10 8:32 下午
 * @Description 用户服务实现类
 * @Author Sxy
 */

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    public User findByUserName(String username) {
        User user =  userDao.findByUsername(username);
        if (user == null) {
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);
        for (Role role : roles) {
            Integer roleId = role.getId();
            //根据角色id查询关联权限
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            role.setPermissions(permissions);   //角色关联权限
        }
        user.setRoles(roles);
        return user;
    }
}
