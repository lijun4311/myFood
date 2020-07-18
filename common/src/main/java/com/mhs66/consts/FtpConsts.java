package com.mhs66.consts;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author 76442
 * @date 2020-07-18 17:32
 */
@Component
@ConfigurationProperties(prefix = "ftp")
@PropertySource("classpath:system.properties")
public class FtpConsts {
    /**
     * ftp静态资源前缀
     */
    @Getter
    private static String sourcePrefix="localhost";

    public  void setHttpPrefix(String httpPrefix) {
        FtpConsts.sourcePrefix = httpPrefix;
    }
}
