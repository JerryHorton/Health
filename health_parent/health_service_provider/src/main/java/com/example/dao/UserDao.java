package com.example.dao;

import com.example.pojo.User;

/**
 * @version 1.0
 * @Date 2022/10/10 8:34 下午
 * @Description 用户持久层接口
 * @Author Sxy
 */

public interface UserDao {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 查询结果
     */
    User findByUsername(String username);
}
