package com.czxy.bos.service;

import com.czxy.bos.dao.*;
import com.czxy.bos.take_delivery.WayBill;
import com.czxy.bos.transit.DeliveryInfo;
import com.czxy.bos.transit.InOutStorageInfo;
import com.czxy.bos.transit.SignInfo;
import com.czxy.bos.transit.TransitInfo;
import com.czxy.bos.vo.DataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TransitInfoService
 * @Author 宋明明
 * @Date 2018/10/12 09:33
 * Version 1.0
 **/
@Service
@Transactional
public class TransitInfoService {
    @Resource
    private TransitInfoMapper transitInfoMapper;
    @Resource
    private WayBillMapper wayBillMapper;
    @Resource
    private InOutStorageInfoMapper inOutStorageInfoMapper;
    @Resource
    private DeliveryInfoMapper deliveryInfoMapper;
    @Resource
    private SignMapper signInfoMapper;

    public DataGridResult queryTransitInfoBypage(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        final List<TransitInfo> transitInfos = transitInfoMapper.selectAll();
        for(TransitInfo t:transitInfos){
            WayBill wayBill = wayBillMapper.selectByPrimaryKey(t.getWayBillId());
            t.setWayBill(wayBill);
            //准备出入库的数据
            List<InOutStorageInfo> inOutStorageInfoList = inOutStorageInfoMapper.findInOutStorageInfoByTransitInfoId(t.getId());
            t.setInOutStorageInfos(inOutStorageInfoList);
            //准备派送数据
            DeliveryInfo deliveryInfo = deliveryInfoMapper.selectByPrimaryKey(t.getId());
            t.setDeliveryInfo(deliveryInfo);
            //准备签收信息
            SignInfo signInfo = signInfoMapper.selectByPrimaryKey(t.getId());
            t.setSignInfo(signInfo);
        }

        final PageInfo<TransitInfo> pageInfo = new PageInfo<>(transitInfos);
        final DataGridResult result = new DataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    public TransitInfo queryId(String wayBillNum) {
        //查询数据库中的所有transitinfo
        List<TransitInfo> transitInfos = transitInfoMapper.selectAll();

        //便利给transitinfo对象赋值
        for(TransitInfo t:transitInfos){

            // 放置了WayBill的信息
            t.setWayBill(wayBillMapper.selectByPrimaryKey(t.getWayBillId()));

            // 放置出入库信息 InoutStorageInfo
            List<InOutStorageInfo> inOutStorageInfoList = inOutStorageInfoMapper.findInOutStorageInfoByTransitInfoId(t.getId());
            t.setInOutStorageInfos(inOutStorageInfoList);
            // 放置派送信息 DeliveryInfo------根据transitInfoId获取DevlieryInfo
            // TransitInfo的id和DeliveryInfo 的id一致
            DeliveryInfo deliveryInfo = deliveryInfoMapper.selectByPrimaryKey(t.getId());
            t.setDeliveryInfo(deliveryInfo);
            // 放置签收信息
            SignInfo signInfo = signInfoMapper.selectByPrimaryKey(t.getId());
            t.setSignInfo(signInfo);

            //信息赋值完毕判断运单号，相同则返回
            if (t.getWayBill().getWayBillNum().equals(wayBillNum)){
                return t;
            }
        }


       return new TransitInfo();
    }
}
