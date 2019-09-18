package com.czxy.bos.vo;

import java.util.List;

/**
 * @ClassName DataGridResult
 * @Author
 * @Date 2018/9/4 08:52
 * Version 1.0
 **/
public class DataGridResult {
    private  Long total;
    private List rows;

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
