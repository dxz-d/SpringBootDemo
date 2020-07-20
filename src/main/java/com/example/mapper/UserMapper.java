package com.example.mapper;


import com.example.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author diaoxiuze
 * @date 2020/6/28 10:00
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 用户登录
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username} limit 1")
    User findByUserName(String username);
}























