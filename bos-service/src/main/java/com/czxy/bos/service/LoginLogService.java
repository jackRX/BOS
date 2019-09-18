package com.czxy.bos.service;

import com.czxy.bos.HighChart;
import com.czxy.bos.dao.LoginLogMapper;
import com.czxy.bos.system.LoginLog;
import com.czxy.bos.system.OnlineInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class LoginLogService {
    @Resource
    private LoginLogMapper loginLogMapper;

//    @Resource
//    private OnlineInfoMapper onlineInfoMapper;

    public void insertLogin(int userid,Date datatime){
        final LoginLog loginLog = new LoginLog();
        loginLog.setUserid(userid);
        loginLog.setLogintime(datatime);
        loginLogMapper.insert(loginLog);
    }

    public List<HighChart> queryOnline() {
        final List<LoginLog> loginLogs = loginLogMapper.selectAll();
        List<HighChart> chartList = new ArrayList<HighChart>();
        for (int i=0;i<24;i++){
                final HighChart highChart = new HighChart();
                highChart.setName(i);
                highChart.setData(0);
                chartList.add(highChart);
        }

        for (LoginLog loginLog : loginLogs) {
            final int hours = loginLog.getLogintime().getHours();
            for (HighChart highChart : chartList) {
                if (hours==highChart.getName()){
                    int data = highChart.getData();
                    data+=1;
                    highChart.setData(data);
                }
            }

        }
//        final List<OnlineInfo> list = onlineInfoMapper.selectAll();
//        return list;
        return chartList;
    }
}
