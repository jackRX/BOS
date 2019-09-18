package com.czxy.bos.service;

import com.czxy.bos.dao.StandardMapper;
import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.vo.DataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName StandardService
 * @Author
 * @Date 2018/9/4 08:27
 * Version 1.0
 **/
@Service
@Transactional
public class StandardService  {
    @Resource
    private StandardMapper standardMapper;

    public  void addStandard(Standard standard){
        standardMapper.insert(standard);
    }

    public DataGridResult quaryStandardByPage(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        final List<Standard> select = standardMapper.select(null);
        final PageInfo<Standard> pageInfo = new PageInfo<>(select);
        final DataGridResult result = new DataGridResult();
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    public void  updateStandard(Standard standard){
        standardMapper.updateByPrimaryKey(standard);
    }

    public void deleteStandard(String id){
        final String[] split = id.split(",");
        for (String s : split) {
            final int i = Integer.parseInt(s);
            standardMapper.deleteByPrimaryKey(i);
        }


    }

    public List<Standard> findAllStandard() {
        return standardMapper.selectAll();
    }
}
