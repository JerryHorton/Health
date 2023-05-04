package com.example.dao;

import com.example.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/20 10:59 下午
 * @Description 体检套餐持久层接口
 * @Author Sxy
 */

public interface SetmealDao {
    /**
     * 根据条件分页查询
     *
     * @param queryString 查询条件
     * @return 查询结果
     */
    Page<Setmeal> findByCondition(String queryString);

    /**
     * 新增套餐基本信息
     *
     * @param setmeal 套餐基本信息
     */
    void addSetmeal(Setmeal setmeal);

    /**
     * 添加套餐与检查组之间的关联关系
     *
     * @param map
     */
    void addSetmeal_checkggroup(Map<String, Integer> map);

    /**
     * 根据id查询套餐信息
     *
     * @param id 待查询id
     * @return 查询结果
     */
    Setmeal findById(Integer id);

    /**
     * 根据套餐id查询与其关联的检查组id
     *
     * @param id 套餐id
     * @return 查询结果
     */
    Integer[] findCheckGroupIdsBySetmealId(Integer id);

    /**
     * 更新套餐信息
     * @param setmeal 套餐信息
     */
    void editSetmwal(Setmeal setmeal);

    /**
     *  删除套餐与检查组的关联关系
     * @param setmealId 套餐id
     */
    void deleteSetmealCheckGroup(Integer setmealId);

    /**
     * 根据id删除套餐信息
     *
     * @param id 套餐id
     */
    void deleteSetmeal(Integer id);

    /**
     * 查询所有套餐
     *
     * @return 查询结果
     */
    List<Setmeal> findAll();
}
