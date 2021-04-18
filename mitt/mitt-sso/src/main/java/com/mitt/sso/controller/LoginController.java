package com.mitt.sso.controller;

import com.mitt.common.pojo.User;
import com.mitt.common.utils.Result;
import com.mitt.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mitt
 * @className LoginController
 * @descriotion
 * @date 2021/4/17 17:44
 **/
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping
    public Result loginByUser(User user){
        return loginService.loginByUser(user);
    }

}
