package com.example.service;

import com.example.entity.User;

import java.util.List;

/**
 * @description 用户接口
 * @author diaoxiuze
 * @date 2020/6/28 9:55
 */
public interface UserService {

    /**
     * 用户登录
     * @param userName
     * @return
     */
    User findByUserName(String userName);

}