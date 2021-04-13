package com.mitt.consumer.service;

import com.mitt.common.pojo.User;

import java.util.List;

/**
 * @author mitt
 * @className UserService
 **/
public interface UserService {
    List<User> queryUserByIds(List<Long> ids);
}
