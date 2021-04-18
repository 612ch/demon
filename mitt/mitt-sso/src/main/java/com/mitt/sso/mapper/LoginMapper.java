package com.mitt.sso.mapper;

import com.mitt.common.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mitt
 * @className LoginMapper
 * @descriotion
 * @date 2021/4/17 17:50
 **/
@Mapper
public interface LoginMapper extends tk.mybatis.mapper.common.Mapper<User> {

}
