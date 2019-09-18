package com.czxy.bos.config;

import com.czxy.bos.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @ClassName BosCredentialsMatcher
 * @Author 宋明明
 * @Date 2018/10/9 08:11
 * Version 1.0
 **/
public class BosCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //向下转型
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        //获取用户页面输入的密码
        final String psw = new String(upToken.getPassword());
        //加密
        final String newPsw = Encrypt.md5(psw, upToken.getUsername());
        //获取数据库密码
        final Object dbPsw = info.getCredentials();
        return newPsw.equals(dbPsw);
    }
}
