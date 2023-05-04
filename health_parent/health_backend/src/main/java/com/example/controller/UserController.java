package com.example.controller;

import com.example.constant.MessageConstant;
import com.example.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Date 2022/10/12 8:23 下午
 * @Description 用户操作
 * @Author Sxy
 */

@RequestMapping("/user")
@RestController
public class UserController {
    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            //当Spring security完成认证后，会将当前用户信息保存在框架提供的上下文对象
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                String username = user.getUsername();
                return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
            }
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
