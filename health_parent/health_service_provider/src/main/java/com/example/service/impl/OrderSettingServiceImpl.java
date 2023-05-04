package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.OrderSettingDao;
import com.example.pojo.OrderSetting;
import com.example.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version 1.0
 * @Date 2022/9/24 9:38 上午
 * @Description 预约设置服务实现
 * @Author Sxy
 */

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    //注入dao对象
    @Autowired
    private OrderSettingDao orderSettingDao;

    public void add(List<OrderSetting> data) {
        if (data != null && data.size() > 0) {
            for (OrderSetting orderSetting : data) {
                Date orderDate = orderSetting.getOrderDate();
                long count = orderSettingDao.findCountByOrderDate(orderDate);
                //如果之前已存在该日期的预约数据，则对旧数据进行修改
                if (count > 0) {
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    public List<Map> getOrderSettingByMonth(String date) {
        String[] strings = date.split("-");
        String currentYear = strings[0];
        String currentMonth = strings[1].length() == 1 ? ("0" + strings[1]) : strings[1];
        Map<String, String> map = new HashMap<String, String>();
        map.put("currentYear", currentYear);
        map.put("currentMonth", currentMonth);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<Map>();
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("date", orderSetting.getOrderDate().getDate());
                map1.put("number", orderSetting.getNumber());
                map1.put("reservations", orderSetting.getReservations());
                result.add(map1);
            }
        }
        return result;
    }

    public void editNumberByDate(OrderSetting orderSetting) {
        //根据日期查询是否已经进行了预约设置，若没有则新增预约设置，若有则修改原有的预约设置
        Date date = orderSetting.getOrderDate();
        long count = orderSettingDao.findCountByOrderDate(date);
        if (count > 0) {
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
            orderSettingDao.add(orderSetting);
        }
    }
}
