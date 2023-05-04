package com.example.dao;

import com.example.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/24 9:41 上午
 * @Description 预约设置持久层接口
 * @Author Sxy
 */

public interface OrderSettingDao {
    /**
     * 添加预约设置
     *
     * @param orderSetting 预约设置信息
     */
    void add(OrderSetting orderSetting);

    /**
     * 通过预约日期查询数据条数
     * @param orderDate 日期
     * @return 查得数据条数
     */
    long findCountByOrderDate(Date orderDate);

    /**
     * 通过预约日期查询预约设置详情信息
     *
     * @param orderDate 日期
     * @return 查询结果
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 根据日期修改可预约人数
     *
     * @param orderSetting 预约设置信息
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 根据日期修改已预约人数
     *
     * @param orderSetting 预约设置信息
     */
    void editReservationsByOrderDate(OrderSetting orderSetting);

    /**
     * 根据年月查询预约信息
     *
     * @param map 封装年份月份
     * @return 查询结果
     */
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
}
