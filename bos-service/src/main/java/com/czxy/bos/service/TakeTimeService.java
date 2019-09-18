package com.czxy.bos.service;

import com.czxy.bos.dao.TakeTimeMapper;
import com.czxy.bos.domain.base.TakeTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TakeTimeService
 * @Author
 * @Date 2018/9/13 15:11
 * Version 1.0
 **/
@Service
@Transactional
public class TakeTimeService {
    @Resource
    private TakeTimeMapper takeTimeMapper;

    public List<TakeTime> findAll() {
        List<TakeTime> list = takeTimeMapper.selectAll();
        return list;
    }
}
