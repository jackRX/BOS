package com.czxy.bos.config;

import com.czxy.bos.service.PermissionService;
import com.czxy.bos.service.RoleService;
import com.czxy.bos.service.UserService;
import com.czxy.bos.system.Permission;
import com.czxy.bos.system.Role;
import com.czxy.bos.system.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName BosRealm
 * @Author 宋明明
 * @Date 2018/10/9 08:10
 * Version 1.0
 **/
public class BosRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        System.out.println("授权管理");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据当前登录用户 查询对应角色和权限
//    User user = (User) SecurityUtils.getSubject().getPrincipal();
        User user = (User) pc.getPrimaryPrincipal();
        // 调用业务层，查询角色
        List<Role> roles = roleService.findByUser(user);
        for (Role role : roles) {
            authorizationInfo.addRole(role.getKeyword());
        }
        // 调用业务层，查询权限
        List<Permission> permissions = permissionService.findByUser(user);
        for (Permission permission : permissions) {
            authorizationInfo.addStringPermission(permission.getKeyword());
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1 获取用户页面输入的用户名
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        //2 调用Service查找用户信息
        User user = userService.findUserByUsername(username);
        //3 判断
        if(user==null){
            return  null;
        }
        //表明用户名是对了，得比较密码
        //Object principal, Object credentials, String realmName
        //第一个参数：principal 身份信息---->数据库中查出来的数据
        //第二个参数：credentials 凭证，密码
        //第三个参数：realmName Realm域的名字,当前类名，也可以随意
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return authenticationInfo;
    }
}
