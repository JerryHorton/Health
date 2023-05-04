package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.constant.RedisConstant;
import com.example.dao.CheckGroupDao;
import com.example.dao.CheckItemDao;
import com.example.dao.SetmealDao;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.CheckGroup;
import com.example.pojo.CheckItem;
import com.example.pojo.Setmeal;
import com.example.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2022/9/20 11:01 下午
 * @Description 体检套餐具体服务
 * @Author Sxy
 */

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    //注入dao对象
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CheckItemDao checkItemDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath;  //从属性文件中读取要生成的html对应的目录结构

    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> setmeals = setmealDao.findByCondition(queryString);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(setmeals.getTotal());
        pageResult.setRows(setmeals.getResult());
        return pageResult;
    }

    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐基本信息
        setmealDao.addSetmeal(setmeal);
        //添加套餐与检查组的关联关系
        Integer setmealId = setmeal.getId();
        addSetmeal_checkgroup(setmealId, checkgroupIds);
        //将图片名称保存在redis中
        savePic2Redis(setmeal.getImg());
        //当添加套餐后需要重新生成静态页面(套餐列表页面、套餐详情页面)
        generateMobileStaticHtml();
    }

    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    public Integer[] findCheckGroupIdsBySetmealid(Integer id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //更新套餐基本信息
        setmealDao.editSetmwal(setmeal);
        //删除原有套餐与检查组的关联
        Integer setmealId = setmeal.getId();
        setmealDao.deleteSetmealCheckGroup(setmealId);
        //添加检查组与套餐的关联
        addSetmeal_checkgroup(setmealId, checkgroupIds);
        //当编辑套餐后需要重新生成静态页面(套餐列表页面、套餐详情页面)
        generateMobileStaticHtml();
    }

    public void delete(Integer id) {
        //删除redis中的图片名称，减少资源浪费
        String img = setmealDao.findById(id).getImg();
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, img);
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
        //删除套餐与检查组的关联关系
        setmealDao.deleteSetmealCheckGroup(id);
        //删除套餐信息
        setmealDao.deleteSetmeal(id);
        //当删除套餐后需要重新生成静态页面(套餐列表页面、套餐详情页面)
        generateMobileStaticHtml();
    }

    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    public Setmeal findAllById(Integer id) {
        Setmeal setmeal = setmealDao.findById(id);
        Integer[] checkgroupIds = setmealDao.findCheckGroupIdsBySetmealId(id);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                CheckGroup checkGroup = checkGroupDao.findById(checkgroupId);
                List<Integer> checkItemIds = checkGroupDao.findCheckItemIdsByCheckGroupId(checkgroupId);
                if (checkItemIds != null && checkItemIds.size() > 0) {
                    for (Integer checkItemId : checkItemIds) {
                        CheckItem checkItem = checkItemDao.findById(checkItemId);
                        checkGroup.getCheckItems().add(checkItem);
                    }
                    setmeal.getCheckGroups().add(checkGroup);
                }
            }
        }
        return setmeal;
    }

    /**
     * 添加套餐与检查组关联关系的函数抽取
     *
     * @param setmealId     套餐id
     * @param checkgroupIds 检查组id
     */
    private void addSetmeal_checkgroup(Integer setmealId, Integer[] checkgroupIds) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Integer checkgroupId : checkgroupIds) {
            map.put("setmealId", setmealId);
            map.put("checkgroupId", checkgroupId);
            setmealDao.addSetmeal_checkggroup(map);
        }
    }

    /**
     * 将图片名称保存在redis中，后续删除垃圾图片使用到
     *
     * @param fileName 图片名称
     */
    public void savePic2Redis(String fileName) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, fileName);
    }

    /**
     * 通用方法：用于生成静态页面
     *
     * @param templateName 生成静态页面需要用到的模版名称
     * @param htmlPageName 将要生成的静态页面名称
     * @param map          静态页面中的数据
     */
    public void generateHtml(String templateName, String htmlPageName, Map map) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer writer;
        try {
            Template template = configuration.getTemplate(templateName);
            writer = new FileWriter(new File(outPutPath + "/" + htmlPageName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成套餐列表静态页面
     *
     * @param setmeals 套餐集合
     */
    public void generateMobileSetmealListHtml(List<Setmeal> setmeals) {
        Map map = new HashMap();
        //为模版提供数据
        map.put("setmealList", setmeals);
        generateHtml("mobile_setmeal.ftl", "m_setmeal.html", map);
    }

    /**
     * 生成套餐详情静态页面
     *
     * @param setmeals 套餐集合
     */
    public void generateMobileSetmealDetailHtml(List<Setmeal> setmeals) {
        for (Setmeal setmeal : setmeals) {
            Integer id = setmeal.getId();
            setmeal = findAllById(id);
            Map map = new HashMap();
            map.put("setmeal", setmeal);
            generateHtml("mobile_setmeal_detail.ftl", "m_setmeal_detail_" + id + ".html", map);
        }
    }

    /**
     * 生成静态页面的具体逻辑实现
     */
    public void generateMobileStaticHtml() {
        //生成静态页面前完成数据的封装
        List<Setmeal> setmeals = setmealDao.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmeals);
        //生成套餐详情静态页面
        generateMobileSetmealDetailHtml(setmeals);
    }
}
