package com.mitt.sso.service.impl;

import com.mitt.common.pojo.User;
import com.mitt.common.utils.JwtUtil;
import com.mitt.common.utils.Result;
import com.mitt.sso.mapper.LoginMapper;
import com.mitt.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mitt
 * @className LoginServiceImpl
 * @descriotion
 * @date 2021/4/17 17:46
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginMapper loginMapper;

    @Override
    public Result loginByUser(User user) {
        if (null == user||null==user.getUsername()||null==user.getPassword()) {
           return Result.fail("请输入用户名密码");
        }
        User loginUser = loginMapper.selectOne(user);
        if (null == loginUser) {
           return Result.fail("用户名或者密码错误");
        }
        String token = JwtUtil.signByUser(loginUser.getUsername(), loginUser.getId().toString());
        return Result.ok(token);
    }

}
