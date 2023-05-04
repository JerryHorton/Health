package com.example.service;

import com.example.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/24 9:21 上午
 * @Description 预约设置服务接口
 * @Author Sxy
 */

public interface OrderSettingService {
    /**
     * 添加预约设置数据
     *
     * @param data 预约设置数据
     */
    void add(List<OrderSetting> data);

    /**
     * 获取当前日期下的预约数据
     *
     * @param data 日期(yyyy年MM月)
     * @return 查询结果
     */
    List<Map> getOrderSettingByMonth(String data);

    /**
     * 修改可预约人数
     *
     * @param orderSetting 预约设置信息
     */
    void editNumberByDate(OrderSetting orderSetting);
}
