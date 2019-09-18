package com.czxy.bos.dao;

import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.domain.base.FixedArea;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName FixedAeraMapper
 * @Author
 * @Date 2018/9/6 19:52
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface FixedAeraMapper extends Mapper<FixedArea> {
    @Select("<script>select * from t_fixed_area  where 1=1 " +
            "<if test=\"id != null and id !=''\">  and id like #{id} </if>" +
            "<if test=\"company != null and company !=''\">  and company like #{company} </if>" +
            "</script>")
    @Results({
            @Result(id=true,property="id",column="ID"),
    })
    List<FixedArea> findFixArea(FixedArea fixedArea);
}
