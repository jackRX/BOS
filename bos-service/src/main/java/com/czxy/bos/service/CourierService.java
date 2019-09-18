package com.czxy.bos.service;

import com.czxy.bos.dao.CourierMapper;
import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.vo.DataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CourierService
 * @Author
 * @Date 2018/9/4 11:39
 * Version 1.0
 **/
@Service
@Transactional
public class CourierService {
    @Resource
    private CourierMapper courierMapper;

    public void addCourier(Courier courier){
        courierMapper.insert(courier);
    }

    public DataGridResult findCourierByPage(Integer page,Integer rows,Courier courier){
        PageHelper.startPage(page,rows);

        if(StringUtils.isNotBlank(courier.getCourierNum())){
            courier.setCourierNum("%"+courier.getCourierNum()+"%");
        }
        if(StringUtils.isNotBlank(courier.getCompany())){
            courier.setCompany("%"+courier.getCompany()+"%");
        }
        if(StringUtils.isNotBlank(courier.getType())){
            courier.setType("%"+courier.getType()+"%");
        }
        if (courier.getStandard()==null){
            courier.setStandardId(1);
        }
        final List<Courier> couriers = courierMapper.findCourier(courier);
        final PageInfo<Courier> pageInfo = new PageInfo<>(couriers);
        final DataGridResult result = new DataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    public List<Courier> findNoAssociationCouriers() {
        List<Courier> list = courierMapper.findNoAssociationCouriers();
        return list;
    }


    /**
     * 查找与xxx定区关联的快递员信息
     * @return
     */
    public List<Courier> findAssociationCouriers(String fixedAreaId){
        List<Courier> list = courierMapper.findAssociationCouriers(fixedAreaId);
        return list;
    }
}
