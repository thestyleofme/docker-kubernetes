package com.imooc.user.mapper;

import com.imooc.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select id, username, real_name as realName, password, mobile, email from pe_user " +
            "where id=#{id}")
    UserInfo getUserById(@Param("id") int id);

    @Select("select id, username, real_name as realName, password, mobile, email from pe_user " +
            "where username=#{username}")
    UserInfo getUserByName(@Param("username") String username);

    @Insert("insert into pe_user(username, password, real_name, mobile, email)" +
            "values(#{user.username}, #{user.password}, #{user.realName}, #{user.mobile}, #{user.email})")
    void registerUser(@Param("user") UserInfo userInfo);

    @Select("select u.id,u.username,u.password,u.real_name as realName,u.mobile,u.email,t.intro,t.stars " +
            "from pe_user u,pe_teacher t where u.id=#{id} and u.id = t.user_id ")
    UserInfo getTeacherById(@Param("id") int id);
}
