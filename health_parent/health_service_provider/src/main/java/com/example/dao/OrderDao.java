package com.example.dao;

import com.example.pojo.Order;

import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/28 3:27 下午
 * @Description 预约服务持久层接口
 * @Author Sxy
 */

public interface OrderDao {
    /**
     * 添加预约
     *
     * @param order 预约详情
     */
    void add(Order order);

    /**
     * 根据条件去查询预约
     *
     * @param theOrder 封装相关查询条件的对象
     * @return 查询结果
     */
    Order findByCondition(Order theOrder);

    /**
     * 根据id查询预约订单详情结果
     *
     * @param id 预约订单id
     * @return 查询结果
     */
    Map findById(Integer id);
}
