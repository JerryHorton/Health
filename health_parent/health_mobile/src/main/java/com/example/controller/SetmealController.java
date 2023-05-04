package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.entity.Result;
import com.example.pojo.Setmeal;
import com.example.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @Date 2022/9/25 12:21 下午
 * @Description 套餐管理
 * @Author Sxy
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference      //从zookeeper注册中心查找服务
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> setmeals = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeals);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findAllById(id);
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }
}
