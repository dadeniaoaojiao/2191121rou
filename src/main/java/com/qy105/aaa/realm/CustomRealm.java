package com.qy105.aaa.realm;


import com.qy105.aaa.model.Perm;
import com.qy105.aaa.model.Role;
import com.qy105.aaa.model.User;
import com.qy105.aaa.service.userservice.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println(authenticationToken);
        String principal = (String) authenticationToken.getPrincipal();
        authenticationToken.getCredentials();
        User userInfo = userService.login(principal);
        if (userInfo == null) {
            throw new UnknownAccountException("用户名不存在");
        }
        if (userInfo.getUserStatus() == 0) {
            throw new LockedAccountException("该用户已经被锁定");
        }
        ByteSource byteSource = new SimpleByteSource(principal);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userInfo.getUserName(), userInfo.getUserPassword(), byteSource, this.getName());
        return simpleAuthenticationInfo;
    }
    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String userName = (String) principalCollection.getPrimaryPrincipal();
        List<Role> roles = userService.getRoleByUserName(userName);
        System.out.println(roles);
        for (int i = 0; i < roles.size(); i++) {
            simpleAuthorizationInfo.addRole(roles.get(i).getRoleName());
        }
        List<Perm> perms = userService.getPermByUserName(userName);
        System.out.println(perms);
        for (Perm perm : perms) {
            simpleAuthorizationInfo.addStringPermission(perm.getResourcePerm());
        }
        return simpleAuthorizationInfo;
    }

}
