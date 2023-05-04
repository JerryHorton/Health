package com.example.dao;

import com.example.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @version 1.0
 * @Date 2022/9/8 10:00 下午
 * @Description 检查项持久层接口
 * @Author Sxy
 */

public interface CheckItemDao {
    /**
     * 新增检查项
     * @param checkItem 新增项
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     *
     * @param queryString 查询条件
     * @return 查询结果，封装进Page对象中
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 根据id删除检查项
     *
     * @param id 待删除项id
     */
    void deleteById(Integer id);

    /**
     *
     * @param id 检查项id
     * @return 该检查项在关联表中出现的数目
     */
    long findCountByCheckItemId(Integer id);

    /**
     * 编辑检查项
     *
     * @param checkItem 目标结果
     */
    void edit(CheckItem checkItem);

    /**
     * 根据id查询检查项
     * @param id 待查询id
     * @return 查询数据
     */
    CheckItem findById(Integer id);

    /**
     * 查询所有检查项
     *
     * @return 查询结果
     */
    List<CheckItem> findAll();
}


