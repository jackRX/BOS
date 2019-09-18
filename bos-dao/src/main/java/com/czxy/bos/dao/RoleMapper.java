package com.czxy.bos.dao;

import com.czxy.bos.system.Role;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName RoleMapper
 * @Author 宋明明
 * @Date 2018/10/9 08:49
 * Version 1.0
 **/
@org.apache.ibatis.annotations.Mapper
public interface RoleMapper extends Mapper<Role> {

    @Select("SELECT * FROM t_role r WHERE r.id IN (SELECT ur.role_id FROM t_user_role ur WHERE ur.user_id = #{userId})")
    List<Role> findByUser(int userId);
}
