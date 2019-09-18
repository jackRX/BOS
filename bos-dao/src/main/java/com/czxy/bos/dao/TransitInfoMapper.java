package com.czxy.bos.dao;

import com.czxy.bos.take_delivery.WayBill;
import com.czxy.bos.transit.TransitInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName TransitInfoMapper
 * @Author 宋明明
 * @Date 2018/10/12 09:29
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface TransitInfoMapper extends Mapper<TransitInfo> {

    @Select("select * from t_way_bill w,t_transit_info t where way_bill_num = #{wayBillNum} and w.ID=t.WAYBILL_ID;")
    TransitInfo queryId(String wayBillNum);
}
