package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.example.constant.MessageConstant;
import com.example.constant.RedisMessageConstant;
import com.example.entity.Result;
import com.example.pojo.Member;
import com.example.service.LoginService;
import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/30 2:24 下午
 * @Description 登录管理
 * @Author Sxy
 */

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference  //从zookeeper注册中心查找服务
    private LoginService loginService;
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 邮箱登录验证码校验
     *
     * @param map 登录参数
     * @return 执行结果
     */
    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        String email = (String) map.get("email");
        String validateCode = (String) map.get("validateCode");
        String codeInRedis = jedisPool.getResource().get(email + RedisMessageConstant.SENDTYPE_LOGIN);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {     //验证码校验失败
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {    //验证成功
            if (memberService.findByEmail(email) == null) {     //若改用户之前未注册过，则加入会员信息
                Member member = new Member();
                member.setEmail(email);
                memberService.add(member);
                //写入Cookie，跟踪用户
                Cookie cookie = new Cookie("login_member_email", email);
                cookie.setPath("/");//路径
                cookie.setMaxAge(60 * 60 * 24 * 30);//有效期30天
                response.addCookie(cookie);
                //保存会员信息到Redis中
                String json = JSON.toJSON(member).toString();
                jedisPool.getResource().setex(email, 60 * 30, json);
            }
            //登录成功
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
    }
}
