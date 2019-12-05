package com.qy105.aaa.mapper;

import com.qy105.aaa.model.Perm;
import com.qy105.aaa.model.Role;
import com.qy105.aaa.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     *
     * @param name
     * @return
     */
    @Select("select * from user_info where user_name=#{name}")
    User login(String name);
    @Insert("insert into user_info values(null,#{userName},#{userPassword},1)")
    void ins(User user);
    @Select("select * from role_info where role_id in (select role_id from user_role_info where user_id in(select user_id from user_info where user_name = #{userName}) )")
    List<Role> getRoleByUserName(String userName);

    @Select("select * from perm_info where per_id in(select res_id from role_res_info where role_id in(select role_id from user_role_info where user_id in(select user_id from user_info where user_name = #{userName})))")
    List<Perm> getPermByUserName(String userName);
}
