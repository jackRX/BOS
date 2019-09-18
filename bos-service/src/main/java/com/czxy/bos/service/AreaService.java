package com.czxy.bos.service;

import com.czxy.bos.dao.AreaMapper;
import com.czxy.bos.domain.base.Area;
import com.czxy.bos.vo.DataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AreaService
 * @Author
 * @Date 2018/9/6 11:13
 * Version 1.0
 **/
@Service
@Transactional
public class AreaService {
    @Resource
    private AreaMapper areaMapper;


    public void saveAreas(List<Area> areaList){
        for (Area area : areaList) {
            areaMapper.insert(area);
        }
    }

    public DataGridResult findAllArea(Integer page,Integer rows,Area area){
        PageHelper.startPage(page,rows);

        if (StringUtils.isNotBlank(area.getCity())){
        area.setCity("%"+area.getCity()+"%");
        }
        if (StringUtils.isNotBlank(area.getProvince())){
        area.setProvince("%"+area.getProvince()+"%");
        }
        if (StringUtils.isNotBlank(area.getDistrict())){
        area.setDistrict("%"+area.getDistrict()+"%");
        }

        final List<Area> areas = areaMapper.findAllArea(area);

        final PageInfo<Area> pageInfo = new PageInfo<>(areas);
        final DataGridResult result = new DataGridResult();
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    public List<Area> findAllAreas() {
        final List<Area> areas = areaMapper.selectAll();
        return areas;

    }
}
