package com.example.service;

import com.example.entity.Result;

import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/28 3:24 下午
 * @Description 预约服务接口
 * @Author Sxy
 */

public interface OrderService {
    /**
     * 添加用户预约信息
     *
     * @param map 预约信息
     * @return 执行结果
     */
    Result submit(Map map) throws Exception;

    /**
     * 根基id查询预约订单信息
     *
     * @param id 预约订单id
     * @return 查询结果
     */
    Map findById(Integer id);
}
