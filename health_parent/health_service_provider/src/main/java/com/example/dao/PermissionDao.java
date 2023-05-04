package com.example.dao;

import com.example.pojo.Permission;

import java.util.Set;

/**
 * @version 1.0
 * @Date 2022/10/10 8:54 下午
 * @Description
 * @Author Sxy
 */

public interface PermissionDao {
    /**
     * 根据角色id查询关联的权限
     *
     * @param roleId 角色id
     * @return 查询结果
     */
    Set<Permission> findByRoleId(Integer roleId);
}
