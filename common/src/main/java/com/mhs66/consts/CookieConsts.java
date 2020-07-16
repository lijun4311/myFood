package com.mhs66.consts;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * description 全局cookie 配置常量
 *
 * @author lijun
 * @date 2020-06-16 19:30
 * @since version-1.0
 */
@Component
@ConfigurationProperties(prefix = "cookie")
@PropertySource("classpath:cookie.properties")
public class CookieConsts {
    /**
     * domain域信息
     */
    @Getter
    private static String domain = "localhost";
    /**
     * path根路径
     */
    @Getter
    private static String path = "/";
    /**
     * cookie 存储标识
     */
    @Getter
    private static String token = "token";

    public void setDomain(String domain) {
        CookieConsts.domain = domain;
    }

    public void setPath(String path) {
        CookieConsts.path = path;
    }

    public void setToken(String token) {
        CookieConsts.token = token;
    }
}
