package com.czxy.bos.dao;

import com.czxy.bos.take_delivery.WayBill;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName WayBillMapper
 * @Author 宋明明
 * @Date 2018/9/25 10:46
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface WayBillMapper extends Mapper<WayBill> {

    @Select("select * from t_way_bill where way_bill_num = {wayBillNum}")
    WayBill queryId(String wayBillNum);
}
