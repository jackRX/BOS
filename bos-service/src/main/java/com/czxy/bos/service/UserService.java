package com.czxy.bos.service;

import com.czxy.bos.dao.UserMapper;
import com.czxy.bos.system.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
@Transactional
public class UserService {
    @Resource
    private UserMapper userMapper;


    public User findUserByUsername(String username) {
        final Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);
        return userMapper.selectOneByExample(example);
    }
}
