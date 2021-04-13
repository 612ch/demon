package com.mitt.user.controller;

import com.mitt.common.pojo.User;
import com.mitt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mitt
 * @className UserController
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User queryUserById(@PathVariable Long id){
        return this.userService.queryUserById(id);
    }

}
