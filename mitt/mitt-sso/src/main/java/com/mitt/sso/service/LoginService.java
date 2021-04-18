package com.mitt.sso.service;

import com.mitt.common.pojo.User;
import com.mitt.common.utils.Result;

/**
 * @author mitt
 * @className LoginService
 * @descriotion
 * @date 2021/4/17 17:46
 **/
public interface LoginService {
    Result loginByUser(User user);
}
