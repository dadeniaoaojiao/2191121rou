package com.qy105.aaa.service.userservice.impl;

import com.qy105.aaa.mapper.UserMapper;
import com.qy105.aaa.model.Perm;
import com.qy105.aaa.model.Role;
import com.qy105.aaa.model.User;
import com.qy105.aaa.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String name) {
        return userMapper.login(name);
    }

    @Override
    public void ins(User user) {
        userMapper.ins(user);
    }

    @Override
    public List<Role> getRoleByUserName(String userName) {
        List<Role> list = userMapper.getRoleByUserName(userName);
        return list;
    }

    @Override
    public List<Perm> getPermByUserName(String userName) {
        return userMapper.getPermByUserName(userName);
    }
}
