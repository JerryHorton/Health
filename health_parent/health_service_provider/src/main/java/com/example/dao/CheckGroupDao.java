package com.example.dao;

import com.example.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/13 10:22 下午
 * @Description 检查组持久层接口
 * @Author Sxy
 */

public interface CheckGroupDao {
    /**
     * 新建检查组
     *
     * @param checkGroup 检查组
     */
    void addGroup(CheckGroup checkGroup);

    /**
     * 建立检查组与检查项的关联
     *
     * @param map 存储了检查组id与检查项id
     */
    void addGroup_Items(Map<String, Integer> map);

    /**
     * 分页查询
     *
     * @param queryString 查询条件
     * @return 查询结果
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 根据检查组id查询与其关联的检查项id
     *
     * @param id 检查组id
     * @return 查询结果
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 根据id查询检查组
     *
     * @param id 检查组id
     * @return 查询结果
     */
    CheckGroup findById(Integer id);

    /**
     * 编辑检查组信息
     *
     * @param checkGroup 目标检查组信息
     */
    void editGroup(CheckGroup checkGroup);

    /**
     * 删除与检查组与检查项的关联关系
     *
     * @param id 检查组id
     */
    void deleteGroup_Item(Integer id);

    /**
     * 删除检查组
     *
     * @param id 带删除id
     */
    void deleteGroup(Integer id);

    /**
     * 查询所有检查组
     *
     * @return 查询结果
     */
    List<CheckGroup> findAll();
}
