package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.constant.MessageConstant;
import com.example.dao.MemberDao;
import com.example.dao.OrderDao;
import com.example.dao.OrderSettingDao;
import com.example.entity.Result;
import com.example.pojo.Member;
import com.example.pojo.Order;
import com.example.pojo.OrderSetting;
import com.example.service.OrderService;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/28 3:25 下午
 * @Description 预约服务具体实现
 * @Author Sxy
 */

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    public Result submit(Map map) throws Exception {
        //添加预约逻辑实现
        String idCard = (String) map.get("idCard");
        String date = (String) map.get("orderDate");
        Date orderDate = DateUtils.parseString2Date(date);
        //判断该用户是否第一次进行预约业务，若是则补全会员信息
        Member member = memberDao.findByIdCard(idCard);
        if (member == null) {   //该会员信息为补全身份证号
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard(idCard);
            memberDao.editByEmail(member);
        }
        //判断用户指定预约时间是否满足可提供服务时间
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if (count == 0) {    //当前时间不提供服务,无法预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //判断用户是否重复预约(同一天重复预约同一个套餐)
        Order theOrder = new Order();
        theOrder.setOrderDate(orderDate);
        theOrder.setMemberId(member.getId());
        theOrder.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        Order find = orderDao.findByCondition(theOrder);
        if (find != null) {
            return new Result(false, MessageConstant.HAS_ORDERED);
        }
        //检查预约时间的可预约人数是否已满
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (number == reservations) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //预约人数未满则更新已预约人数
        orderSetting.setReservations(reservations + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        //添加预约信息
        Order order = new Order(member.getId(),
                orderDate,
                (String) map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    public Map findById(Integer id) {
        return orderDao.findById(id);
    }
}
