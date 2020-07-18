package com.mhs66.consts;

import com.mhs66.utils.IDateUtil;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * description:
 * 用户通用常量
 *
 * @author 76442
 * @date 2020-07-18 17:20
 */
@Component
@ConfigurationProperties(prefix = "user")
@PropertySource("classpath:system.properties")
public class UserConsts {
    @Getter
    private static String defaultFace = "";
    @Getter
    private static LocalDate defaultBirhday = LocalDate.now();
    @Getter
    private static Integer defaultSex = 2;
    @Getter
    private static String userPrefix = "user";

    public  void setDefaultBirhday(String defaultBirhday) {
        UserConsts.defaultBirhday = IDateUtil.strToDate(defaultBirhday);
    }

    public  void setDefaultSex(String defaultSex) {

        UserConsts.defaultSex = Integer.parseInt(defaultSex);
    }

    public void setDefaultFace(String defaultFace) {
        UserConsts.defaultFace = defaultFace;
    }

    public  void setUserPrefix(String userPrefix) {
        UserConsts.userPrefix = userPrefix;
    }
}
