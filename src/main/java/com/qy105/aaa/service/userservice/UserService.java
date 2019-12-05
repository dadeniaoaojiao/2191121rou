package com.qy105.aaa.service.userservice;

import com.qy105.aaa.model.Perm;
import com.qy105.aaa.model.Role;
import com.qy105.aaa.model.User;

import java.util.List;

public interface UserService {
    User login(String name);

    void ins(User user);

    List<Role> getRoleByUserName(String userName);

    List<Perm> getPermByUserName(String userName);
}
