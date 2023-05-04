package com.example.dao;

import com.example.pojo.Role;

import java.util.Set;

/**
 * @version 1.0
 * @Date 2022/10/10 8:51 下午
 * @Description 角色持久层接口
 * @Author Sxy
 */

public interface RoleDao {
    /**
     * 根据用户id查询具体角色信息
     *
     * @param userId 用户id
     * @return 查询结果
     */
    Set<Role> findByUserId(Integer userId);
}
