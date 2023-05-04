package com.example.controller;

import com.example.constant.MessageConstant;
import com.example.constant.RedisMessageConstant;
import com.example.entity.Result;
import com.example.utils.MailUtils;
import com.example.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @version 1.0
 * @Date 2022/9/28 2:11 下午
 * @Description 邮箱验证码管理
 * @Author Sxy
 */

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送预约业务邮箱验证码
     *
     * @param email 用户邮箱
     * @return 执行结果
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String email) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        try {
            //发送邮件
            String text = "【传智健康】：你的预约验证码为" + code.toString();
            MailUtils.sendMail(email, text, "预约申请");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("邮箱验证码：" + code);
        //将生成的验证码缓存到redis
        jedisPool.getResource().setex(
                email + RedisMessageConstant.SENDTYPE_ORDER, 30, code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String email) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        try {
            //发送邮件
            String text = "【传智健康】：您的登录验证码为：" + code.toString();
            MailUtils.sendMail(email, text, "登录验证");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("邮箱验证码：" + code);
        //将生成的验证码缓存到redis
        jedisPool.getResource().setex(
                email + RedisMessageConstant.SENDTYPE_LOGIN, 30, code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
