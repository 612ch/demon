package com.mitt.consumer.controller;

import com.mitt.common.util.Result;
import com.mitt.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mitt
 * @className ClientController
 **/
@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    UserService userService;

    @GetMapping
    public Result queryUserByIds(@RequestParam("ids") List<Long> ids){
        return Result.ok(userService.queryUserByIds(ids));
    }
}
