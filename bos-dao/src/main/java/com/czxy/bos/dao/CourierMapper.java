package com.czxy.bos.dao;

import com.czxy.bos.domain.base.Courier;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName CourierMapper
 * @Author
 * @Date 2018/9/4 11:37
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface CourierMapper extends Mapper<Courier> {

/*
<script>select * from sys_user where 1=1 "
			+ "<if test='usercode != null'> and usercode like #{usercode} </if>"
			+ "<if test='username != null'> and username like #{username} </if>"
			+ "<if test= \"groupid != null and groupid != '' \"> and groupid = #{groupid} </if>"
			+"</script>"
* */
    @Select("<script> select * from t_courier where 1=1 "
            +" <if test=\"courierNum != null and courierNum !='' \">  and courier_num like #{courierNum} </if>"
            +" <if test=\"company != null and company !='' \">  and company like #{company} </if>"
            +" <if test=\"type != null and type !='' \">  and type like #{type} </if>"
            +" <if test= \"standardId =='' and standard.id !=''\">  and standard_id = #{standard.id} </if>"
            +  " </script>")
    @Results({
            @Result(id=true,property="id",column="ID"),
            @Result(property = "standard", column = "STANDARD_ID",
                    one = @One(select = "com.czxy.bos.dao.StandardMapper.selectByPrimaryKey")
            )
    })
     List<Courier> findCourier(Courier courier);

    @Select("select * from t_courier where id not in (select DISTINCT(courier_id) from t_fixedarea_courier)")
    List<Courier> findNoAssociationCouriers();

    @Select("select * from t_courier where id in (select DISTINCT(courier_id) from t_fixedarea_courier where FIXED_AREA_ID = #{fixedAreaId})")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "courierNum",column = "courier_num"),
            @Result(property = "standard" ,column = "standard_id",
                    one = @One(select = "com.czxy.bos.dao.StandardMapper.selectByPrimaryKey")
            ),
            @Result(property = "taketime",column = "taketime_id",
                    one = @One(select = "com.czxy.bos.dao.TakeTimeMapper.selectByPrimaryKey")
            )
    })
    public List<Courier> findAssociationCouriers(String fixedAreaId);
}
