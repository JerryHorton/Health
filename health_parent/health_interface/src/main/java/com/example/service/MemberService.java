package com.example.service;

import com.example.pojo.Member;

/**
 * @version 1.0
 * @Date 2022/9/28 10:21 下午
 * @Description 会员管理业务层接口
 * @Author Sxy
 */

public interface MemberService {
    /**
     * 根据邮箱查找会员信息
     *
     * @param email 邮箱
     * @return 查询结果
     */
    Member findByEmail(String email);

    /**
     * 添加会员信息
     *
     * @param member 会员信息
     */
    void add(Member member);

    /**
     * 根据会员邮箱休息信息
     *
     * @param member 更新后的会员信息
     */
    void editByEmail(Member member);
}
