package com.mitt.gateway.filter;

import com.mitt.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author mitt
 * @className LoginFilter
 **/
@Component
@Slf4j
public class LoginFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求参数access-token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        //2.判断是否存在
        if (token == null) {
            //3.如果不存在 : 认证失败
            log.debug("没有登录");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); //请求结束
        } else if (!JwtUtil.verifyByUser(token)) {
            //3.如果失效 : 认证失败
            log.debug("token失效");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); //请求结束
        }
        //4.如果存在,继续执行
        return chain.filter(exchange); //继续向下执行
    }
}
