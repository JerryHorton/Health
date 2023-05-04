package com.example.constant;

/**
 * @version 1.0
 * @Date 2022/9/25 11:04 上午
 * @Description
 * @Author Sxy
 */

public class RedisMessageConstant {
    public static final String SENDTYPE_ORDER = "001";//用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_LOGIN = "002";//用于缓存邮箱号快速登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "003";//用于缓存找回密码时发送的验证码
}
