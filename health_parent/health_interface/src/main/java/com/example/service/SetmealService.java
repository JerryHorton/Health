package com.example.service;

import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.Setmeal;

import java.util.List;

/**
 * @version 1.0
 * @Date 2022/9/20 11:00 下午
 * @Description 体检套餐服务接口
 * @Author Sxy
 */

public interface SetmealService {
    /**
     * 分页查询
     *
     * @param queryPageBean 分页参数
     * @return 查询结果
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 添加体检套餐
     *
     * @param setmeal       套餐基本信息
     * @param checkgroupIds 与套餐关联的检查组id
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据id查询套餐信息
     *
     * @param id 带查询id
     * @return 查询结果
     */
    Setmeal findById(Integer id);

    /**
     * 根据id查询与套餐想关联的检查组id
     * @param id 套餐id
     * @return 查询结果
     */
    Integer[] findCheckGroupIdsBySetmealid(Integer id);

    /**
     * 跟新套餐信息
     *
     * @param setmeal 套餐基本信息
     * @param checkgroupIds 与套餐关联的检查组id
     */
    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据id删除套餐
     *
     * @param id 套餐id
     */
    void delete(Integer id);

    /**
     * 查询所有套餐
     *
     * @return 查询结果
     */
    List<Setmeal> findAll();

    /**
     * 根据套餐id查询套餐基本信息、所包含的检查组以及检查组所包含的检查项
     *
     * @param id 套餐id
     * @return 查询结果
     */
    Setmeal findAllById(Integer id);
}
