package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.CheckGroupDao;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.CheckGroup;
import com.example.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/13 10:21 下午
 * @Description 检查组具体服务
 * @Author Sxy
 */

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    //注入dao对象
    @Autowired
    private CheckGroupDao checkGroupDao;

    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组
        checkGroupDao.addGroup(checkGroup);
        //设置检查组与检查项的关联关系
        Integer id = checkGroup.getId();
        setGroup_Item(checkitemIds, id);
    }

    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> checkGroups = checkGroupDao.findByCondition(queryString);
        PageResult pageResult = new PageResult();
        pageResult.setRows(checkGroups.getResult());
        pageResult.setTotal(checkGroups.getTotal());
        return pageResult;
    }

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
       return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        Integer id = checkGroup.getId();
        //编辑检查组
        checkGroupDao.editGroup(checkGroup);
        //先删除检查组与检查项的关联关系
        checkGroupDao.deleteGroup_Item(id);
        //再添加新的检查组与检查项的关联关系
        setGroup_Item(checkitemIds, id);
    }

    public void delete(Integer id) {
        //由于外键约束，先删除检查组与检查项的对应关系
        checkGroupDao.deleteGroup_Item(id);
        //删除检查组
        checkGroupDao.deleteGroup(id);
    }

    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    //公共方法抽取：添加检查组与检查项的关联关系
    private void setGroup_Item(Integer[] checkitemIds, Integer id) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Integer checkitemId : checkitemIds) {
                map.put("checkGroupId", id);
                map.put("checkItemId", checkitemId);
                checkGroupDao.addGroup_Items(map);
            }
        }
    }
}
