package com.example.service;

import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.CheckItem;

import java.util.List;

/**
 * @version 1.0
 * @Date 2022/9/8 9:49 下午
 * @Description 检查项服务接口
 * @Author Sxy
 */

public interface CheckItemService {
    /**
     * 新增检查项
     *
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     *
     * @param queryPageBean 分页参数
     * @return 查询结果
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项
     *
     * @param id 带删除项id
     */
    void deleteById(Integer id);

    /**
     * 修改检查项
     *
     * @param checkItem 目标结果
     */
    void update(CheckItem checkItem);

    /**
     * 根据id查询检查项
     *
     * @param id 带查询id
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
