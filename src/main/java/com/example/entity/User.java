package com.example.entity;

import lombok.Data;

/**
 * @author diaoxiuze
 * @date 2020/6/28 9:53
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
}
