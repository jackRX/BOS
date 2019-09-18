package com.czxy.bos.service;

import com.czxy.bos.dao.RoleMapper;
import com.czxy.bos.system.Role;
import com.czxy.bos.system.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName RoleService
 * @Author 宋明明
 * @Date 2018/10/9 08:51
 * Version 1.0
 **/
@Service
@Transactional
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    public List<Role> findByUser(User user) {
        if(user.getUsername().equals("admin")){
            return roleMapper.selectAll();
        }
        return roleMapper.findByUser(user.getId());
    }
}
