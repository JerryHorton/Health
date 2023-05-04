package com.example.jobs;

import com.example.constant.RedisConstant;
import com.example.utils.QiNiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @version 1.0
 * @Date 2022/9/22 8:38 下午
 * @Description 自定义定时清理垃圾图片
 * @Author Sxy
 */

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        //根据redis中保存的两个set集合进行差值计算，获得垃圾图片的名称集合
        Set<String> rubbish = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (rubbish != null) {
            for (String s : rubbish) {
                //从服务器中删除图片
                QiNiUtils.deleteFileFromQiNiu(s);
                //从redis中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, s);
                System.out.println("定时清理垃圾图片任务完成...");
            }
        }
    }
}
