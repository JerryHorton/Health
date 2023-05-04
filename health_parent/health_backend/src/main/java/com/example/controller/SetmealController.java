package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.constant.RedisConstant;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.entity.Result;
import com.example.pojo.Setmeal;
import com.example.service.SetmealService;
import com.example.utils.QiNiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @version 1.0
 * @Date 2022/9/20 10:57 下午
 * @Description 体检套餐管理
 * @Author Sxy
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference  //从zookeeper服务注册中心查找服务
    private SetmealService setmealService;

    //使用jedisPool操作redis服务
    @Autowired
    private JedisPool jedisPool;

    /**
     * 文件上传
     *
     * @return 执行结果
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + extension;
        try {
            //将文件上传到七牛云服务器
            QiNiUtils.upload2QiNiu(imgFile.getBytes(), fileName);
            //将上传的文件名保存在redis中的set集合中，删除垃圾图片会使用到
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean 分页参数
     * @return 查询结果
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.pageQuery(queryPageBean);
    }

    /**
     * 新增套餐信息
     *
     * @param setmeal       套餐基本信息
     * @param checkgroupIds 与套餐关联的检查组id
     * @return 执行结果
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id 待查询id
     * @return 查询结果
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.FIND_CHECKITEM_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.FIND_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id查询与套餐具有关联的检查组id
     *
     * @param id 套餐id
     * @return 查询结果
     */
    @RequestMapping("/findCheckGroupIdsBySetmealid")
    public Result findCheckGroupIdsBySetmealid(Integer id) {
        try {
            Integer[] chickgroupIds = setmealService.findCheckGroupIdsBySetmealid(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, chickgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 更新检查
     *
     * @param setmeal       套餐信息
     * @param checkgroupIds 与套餐关联的检查组信息
     * @return 查询结果
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.edit(setmeal, checkgroupIds);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            setmealService.delete(id);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_SUCCESS);
        }
    }
}
