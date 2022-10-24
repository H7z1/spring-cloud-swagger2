package com.lzh.gateway.filter;

import com.lzh.gateway.Utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Author linzhihao
 * @Date 2022/10/24 12:37 下午
 * @Description
 */
@Slf4j
@Component
public class OtherFilter extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) ->
        {
            log.info("=====url====={}=====",exchange.getRequest().getURI().getPath());
            return ResponseUtil.fail(exchange.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        };
    }
}
