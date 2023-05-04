package com.example.service;

import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.CheckGroup;

import java.util.List;

/**
 * @version 1.0
 * @Date 2022/9/13 10:20 下午
 * @Description 检查组服务接口
 * @Author Sxy
 */

public interface CheckGroupService {
    /**
     * 新增检查组
     *
     * @param checkGroup 检查组
     * @param checkitemIds 关联的检查项id
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页查询
     *
     * @param queryPageBean 分页参数
     * @return 查询结果
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据检查组id查询与其关联的检查项id
     *
     * @param id 检查组id
     * @return 查询结果
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 根据id查询检查组
     * @param id 检查组id
     * @return 查询结果
     */
    CheckGroup findById(Integer id);

    /**
     *  编辑检查组
     *
     * @param checkGroup 目标检查组信息
     * @param checkitemIds 关联的检查项id
     */
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组
     *
     * @param id 待删除id
     */
    void delete(Integer id);

    /**
     * 查询所有检查组
     *
     * @return 查询结果
     */
    List<CheckGroup> findAll();
}
