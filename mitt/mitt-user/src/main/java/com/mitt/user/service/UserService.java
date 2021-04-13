package com.mitt.user.service;

import com.mitt.common.pojo.User;

/**
 * @author ChenHao
 * @className UserService
 **/
public interface UserService {
    User queryUserById(Long id);
}
