package com.mitt.consumer.feign;

import com.mitt.common.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author mitt
 * @className UserFeign
 **/
@FeignClient("user-server")
public interface UserFeign {
    @GetMapping("/user/{id}")
    User queryUserById(@PathVariable Long id);
}
