package com.example.dao;

import com.example.pojo.Member;

/**
 * @version 1.0
 * @Date 2022/9/28 10:22 下午
 * @Description 会员管理持久层接口
 * @Author Sxy
 */

public interface MemberDao {
    /**
     * 根据用户的身份证号查取会员信息
     *
     * @param idCard 身份证号
     * @return 查询结果
     */
    Member findByIdCard(String idCard);

    /**
     * 添加会员信息
     *
     * @param member 会员信息
     */
    void add(Member member);

    /**
     * 根据邮箱查找会员信息
     *
     * @param email 邮箱
     * @return 查询结果
     */
    Member findByEmail(String email);

    /**
     * 修改会员信息
     *
     * @param member 会员信息
     */
    void editByEmail(Member member);
}
