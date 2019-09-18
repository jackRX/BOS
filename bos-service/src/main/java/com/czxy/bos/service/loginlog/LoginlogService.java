package com.czxy.bos.service.loginlog;

import com.czxy.bos.dao.LoginLogMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class LoginlogService {

//    @Resource
//    private LoginlogMapper loginlogMapper;
//
//
//    public PageInfo<Loginlog> findAll(Integer  page, Integer  rows) {
//        PageHelper.startPage(page,rows);
//        List<Loginlog> list = loginlogMapper.selectAll();
//        return new PageInfo<Loginlog>(list);
//    }
}
