package com.example.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Date 2022/9/6 10:34 下午
 * @Description 分页结果封装对象
 * @Author Sxy
 */

public class PageResult implements Serializable {
    private Long total;//总记录数
    private List rows;//当前页结果

    public PageResult() {
        super();
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
