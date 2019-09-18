package com.czxy.bos.controller.transit;

import com.czxy.bos.service.TransitInfoService;
import com.czxy.bos.take_delivery.WayBill;
import com.czxy.bos.transit.TransitInfo;
import com.czxy.bos.vo.DataGridResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/transitInfo")
public class TransitInfoController {

    @Resource
    private TransitInfoService transitInfoService;

    @GetMapping
    public ResponseEntity<DataGridResult> queryTransitInfoByPage(Integer page,Integer rows){
        final DataGridResult result = transitInfoService.queryTransitInfoBypage(page, rows);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/queryId")
    public ResponseEntity<TransitInfo> queryId(String wayBillNum){

        //调用sevice查询信息
        TransitInfo transitInfo=  transitInfoService.queryId(wayBillNum);

        //返回
        return new ResponseEntity<>(transitInfo,HttpStatus.OK);
    }

}
