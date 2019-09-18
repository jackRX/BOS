package com.czxy.bos.dao;

import com.czxy.bos.domain.base.Area;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName AreaMapper
 * @Author
 * @Date 2018/9/6 11:11
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface AreaMapper extends Mapper<Area> {


    @Select("<script>select * from t_area where 1=1  " +
            "<if test=\"province != null and province !='' \">  and province like #{province} </if> " +
            " <if test=\"city != null and city !='' \">  and city like #{city} </if> " +
            " <if test=\"district != null and district !='' \">  and district like #{district} </if> " +
            "</script>")
    @Results({
            @Result(column = "id",property = "id"),
    })
    public List<Area> findAllArea(Area area);
}
