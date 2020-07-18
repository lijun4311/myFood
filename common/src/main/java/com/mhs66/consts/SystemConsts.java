package com.mhs66.consts;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author 76442
 * @date 2020-07-18 22:10
 */
@Component
@ConfigurationProperties(prefix = "system")
@PropertySource("classpath:system.properties")
public class SystemConsts {
    @Getter
    private static boolean addressEnabled = true;

    public void setAddressEnabled(String addressEnabled) {
        SystemConsts.addressEnabled = Boolean.parseBoolean(addressEnabled);
    }
}
