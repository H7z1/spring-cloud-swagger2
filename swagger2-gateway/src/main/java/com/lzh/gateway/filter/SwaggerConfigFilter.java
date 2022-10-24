package com.lzh.gateway.filter;

import com.lzh.gateway.Utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @Author linzhihao
 * @Date 2022/10/24 10:46 上午
 * @Description
 */
@Slf4j
@Component
@Profile({"prod","pre"})
public class SwaggerConfigFilter implements GlobalFilter, Ordered {

    private final String[] skipAuthUrls = new String[]{
            "/**/v2/api-docs","/**/doc.html","/**/swagger-resources/**"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("==========url{}=====", path);
        if (isSkipUrl(path)) {
            log.info("==========已跳过url{}=====", path);
            ServerHttpResponse response = exchange.getResponse();
            return ResponseUtil.fail(response,"生产停止访问");
        }
        return chain.filter(exchange);
    }


    /**
     * 判断当前访问的url是否开头URI是在配置的忽略url列表中
     *
     * @param url
     * @return
     */
    public boolean isSkipUrl(String url) {
        if(StringUtils.isEmpty(url)){
            return false;
        }
        AntPathMatcher matcher = new AntPathMatcher();
        for (String skipAuthUrl : skipAuthUrls) {
            if(matcher.match(skipAuthUrl, url)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
