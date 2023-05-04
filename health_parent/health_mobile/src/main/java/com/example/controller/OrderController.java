package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.constant.RedisMessageConstant;
import com.example.entity.Result;
import com.example.pojo.Order;
import com.example.service.OrderService;
import com.example.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/28 3:10 下午
 * @Description 体检预约管理
 * @Author Sxy
 */

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 邮箱体检预约
     *
     * @param map 预约信息
     * @return 执行结果
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String email = (String) map.get("email");
        //从Redis中获取缓存的验证码
        String codeInRedis = jedisPool.getResource().get(email + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        //校验邮箱验证码
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {     //校验失败
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result;
        //验证码校验通过，完成预约操作
        try {
            //设置预约类型
            map.put("orderType", Order.ORDERTYPE_EMAIL);
            result = orderService.submit(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
        if (result.isFlag()) {  //预约成功发送通知信息
            String text = "【传智健康】：您的体检套餐预约成功";
            MailUtils.sendMail(email, text, "预约成功");
        }
        return result;
    }

    /**
     * 根据id查询预约详情
     *
     * @param id 预约订单id
     * @return 执行结果
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
