package com.czxy.bos.service;

import com.czxy.bos.dao.PermissionMapper;
import com.czxy.bos.system.Permission;
import com.czxy.bos.system.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName PermissionService
 * @Author 宋明明
 * @Date 2018/10/9 08:51
 * Version 1.0
 **/
@Service
@Transactional
public class PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 查找指定用户授权的所有权限（如果是admin用户，将查询所有权限）
     * @param user
     * @return
     */
    public List<Permission> findByUser(User user) {
        if(user.getUsername().equals("admin")){
            return permissionMapper.selectAll();
        }
        return permissionMapper.findByUser(user.getId());
    }
}
