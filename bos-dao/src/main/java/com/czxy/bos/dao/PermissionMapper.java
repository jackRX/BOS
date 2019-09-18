package com.czxy.bos.dao;

import com.czxy.bos.system.Permission;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName PermissionMapper
 * @Author 宋明明
 * @Date 2018/10/9 08:50
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface PermissionMapper extends Mapper<Permission> {

    @Select("SELECT p.* FROM t_permission p " +
            "INNER JOIN t_role_permission rp ON p.ID = rp.PERMISSION_ID " +
            "INNER JOIN t_role r ON rp.ROLE_ID = r.ID " +
            "INNER JOIN t_user_role ur ON r.ID = ur.ROLE_ID " +
            "WHERE ur.user_id = #{userId}")
    public List<Permission> findByUser(int userId);
}
