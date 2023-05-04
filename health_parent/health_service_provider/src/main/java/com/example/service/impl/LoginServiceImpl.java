package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.LoginDao;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version 1.0
 * @Date 2022/9/30 2:26 下午
 * @Description 登录业务的实现类
 * @Author Sxy
 */

@Service(interfaceClass = LoginService.class)
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;
}
