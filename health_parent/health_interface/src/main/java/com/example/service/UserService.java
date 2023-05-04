package com.example.service;

import com.example.pojo.User;

/**
 * @version 1.0
 * @Date 2022/10/10 7:32 下午
 * @Description 用户服务接口
 * @Author Sxy
 */

public interface UserService {
    /**
     * 根据用户名查询用户信息和关联的角色信息，同时需要查询角色关联的权限信息
     *
     * @param userName 用户名
     * @return 查询结果
     */
    User findByUserName(String userName);
}
