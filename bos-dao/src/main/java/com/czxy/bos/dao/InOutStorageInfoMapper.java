package com.czxy.bos.dao;

import com.czxy.bos.transit.InOutStorageInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName InOutStorageInfoMapper
 * @Author 宋明明
 * @Date 2018/10/19 15:56
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface InOutStorageInfoMapper extends Mapper<InOutStorageInfo> {

    @Select("select * from t_in_out_storage_info where TRANSIT_INFO_ID = #{transitInfoId} order by id asc")
    public List<InOutStorageInfo> findInOutStorageInfoByTransitInfoId(int transitInfoId);
}
