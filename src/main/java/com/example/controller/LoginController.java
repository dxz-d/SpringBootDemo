package com.example.controller;

import com.example.entity.JsonResult;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author diaoxiuze
 * @date 2020/6/28 9:30
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * @description  用户登录
     * @author diaoxiuze
     * @date 2020/6/28 14:58
     * @param [username, password, req]
     * @return com.example.entity.JsonResult
     */
    @PostMapping("/user/login")
    public JsonResult login(String username, String password, HttpServletRequest req){
        User user = userService.findByUserName(username);
        if (user==null) {
            return JsonResult.build(-1,"用户名或密码错误");
        }
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        if (pwd.equals(user.getPassword())){
            req.getSession().setAttribute("user",user);
            return JsonResult.ok(user);}
        else {
            return JsonResult.build(-1,"用户名或密码错误");
        }
    }
}
















