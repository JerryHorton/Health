package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.MemberDao;
import com.example.pojo.Member;
import com.example.service.MemberService;
import com.example.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version 1.0
 * @Date 2022/9/28 10:23 下午
 * @Description 会员管理业务具体实现
 * @Author Sxy
 */

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    public Member findByEmail(String email) {
       return memberDao.findByEmail(email);
    }

    public void add(Member member) {
        if (member.getPassword() != null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    public void editByEmail(Member member) {
        memberDao.editByEmail(member);
    }
}
