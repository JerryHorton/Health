package com.example.controller;

import com.example.constant.MessageConstant;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.entity.Result;
import com.example.pojo.CheckItem;
import com.alibaba.dubbo.config.annotation.Reference;
import com.example.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @version 1.0
 * @Date 2022/9/8 9:43 下午
 * @Description 检查项管理
 * @Author Sxy
 */

@RestController
@RequestMapping("/checkItem")
public class CheckItemController {
    @Reference  //从zookeeper服务注册中心查找服务
    private CheckItemService checkItemService;

    /**
     * 新增检查项
     *
     * @return 封装的执行结果对象Result
     */
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     *
     * @param pageBean 分页查询参数
     * @return 查询结果
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @RequestMapping("/findPage")
    public PageResult pageQuery(@RequestBody QueryPageBean pageBean) {
        PageResult pageResult = checkItemService.pageQuery(pageBean);
        return pageResult;
    }

    /**
     * 根据id删除检查项
     *
     * @param id 带删除项id
     * @return 执行结果
     */
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete")
    public Result deleteById(Integer id) {
        try {
            checkItemService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            //执行失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 编辑检查项
     *
     * @param checkItem 修改项
     * @return 执行结果
     */
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.update(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 通过id查询检查项
     *
     * @param id 待查id
     * @return 执行结果
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.FIND_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.FIND_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询所有检查项
     *
     * @return 执行结果
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> items = checkItemService.findAll();
            return new Result(true, MessageConstant.FIND_CHECKITEM_SUCCESS, items);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.FIND_CHECKITEM_FAIL);
        }
    }
}
