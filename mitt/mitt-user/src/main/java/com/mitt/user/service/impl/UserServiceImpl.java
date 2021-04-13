package com.mitt.user.service.impl;

import com.mitt.common.pojo.User;
import com.mitt.user.mapper.UserMapper;
import com.mitt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenHao
 * @className UserServiceImpl
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
