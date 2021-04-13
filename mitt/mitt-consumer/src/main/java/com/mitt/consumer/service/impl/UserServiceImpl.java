package com.mitt.consumer.service.impl;

import com.mitt.common.pojo.User;
import com.mitt.consumer.feign.UserFeign;
import com.mitt.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenHao
 * @className UserServiceImpl
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserFeign userFeign;

    @Override
    public List<User> queryUserByIds(List<Long> ids) {
        List<User> users = new ArrayList<User>();
        ids.forEach(id->{
            users.add(userFeign.queryUserById(id));
        });
        return users;
    }
}
