package com.czxy.bos.service;

import com.czxy.bos.dao.CourierMapper;
import com.czxy.bos.dao.FixedAeraMapper;
import com.czxy.bos.dao.FixedAreaCourierMapper;
import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.domain.base.FixedArea;
import com.czxy.bos.domain.base.FixedAreaCourier;
import com.czxy.bos.vo.DataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName FixedAeraService
 * @Author
 * @Date 2018/9/6 19:55
 * Version 1.0
 **/
@Service
@Transactional
public class FixedAeraService {
    @Resource
    private FixedAeraMapper fixedAeraMapper;
    @Resource
    private CourierMapper courierMapper;
    @Resource
    private FixedAreaCourierMapper fixedAreaCourierMapper;

    public DataGridResult findFixAreaByPage(Integer page, Integer rows, FixedArea fixedArea){
        PageHelper.startPage(page,rows);

        if(StringUtils.isNotBlank(fixedArea.getId())){
            fixedArea.setId("%"+fixedArea.getId()+"%");
        }
        if(StringUtils.isNotBlank(fixedArea.getCompany())){
            fixedArea.setCompany("%"+fixedArea.getCompany()+"%");
        }
        final List<FixedArea> fixArea = fixedAeraMapper.findFixArea(fixedArea);
//        final List<FixedArea> fixedAreas = fixedAeraMapper.selectAll();
        final PageInfo<FixedArea> pageInfo = new PageInfo<>(fixArea);
        final DataGridResult result = new DataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }



    public void addFixedArea(FixedArea fixedArea){
        fixedAeraMapper.insert(fixedArea);
    }

    public void assocationCourier(String fixedAreaId, Integer courierId, Integer takeTimeId) {
        // 快递员关联时间
        Courier courier = courierMapper.selectByPrimaryKey(courierId);
        courier.setTaketimeId(takeTimeId);
        // 提交数据库修改
        courierMapper.updateByPrimaryKey(courier);
        // 定区关联快递员
        FixedAreaCourier fixedAreaCourier = new FixedAreaCourier();
        fixedAreaCourier.setFixedAreaId(fixedAreaId);
        fixedAreaCourier.setCourierId(courierId);

        fixedAreaCourierMapper.insert(fixedAreaCourier);
    }
}
