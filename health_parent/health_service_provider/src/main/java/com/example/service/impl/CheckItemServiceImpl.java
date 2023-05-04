package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.CheckItemDao;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.CheckItem;
import com.example.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @Date 2022/9/8 9:58 下午
 * @Description 检查项具体服务
 * @Author Sxy
 */

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    //注入Dao对象
    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //完成分页查询，基于mybatis框架提供的分页查询插件完成
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> checkItems = checkItemDao.selectByCondition(queryString);
        long total = checkItems.getTotal();
        List<CheckItem> result = checkItems.getResult();
        PageResult pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setRows(result);
        return pageResult;
    }

    public void deleteById(Integer id) {
        //判断当前检查项是否已经关联到检查组，若已关联则不允许删除
        if (checkItemDao.findCountByCheckItemId(id) > 0) {  //当前检查项已被关联，不允许删除
            throw new RuntimeException();
        }
        checkItemDao.deleteById(id);
    }

    public void update(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
