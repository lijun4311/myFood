package com.mhs66.consts;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author lijun
 * @date 2020-06-16 19:38
 * description 系统常量配置
 * @since version-1.0
 */
@Component
@ConfigurationProperties(prefix = "sys")
@PropertySource("classpath:system.properties")
public class SystemConsts {
    /**
     * 密码加盐key
     */

    @Getter
    private static String passwordSalt="mall";
    /**
     * 分页默认当前页
     */
    @Getter
    private static int pageCurrent=1;
    /**
     * 分页总页数
     */
    @Getter
    private static int pageSize=10;
    /**
     * 升序排序
     */
    @Getter
    private static int ascend = 0;
    /**
     * 降序排序
     */
    @Getter
    private static int descend = 1;

    public void setPasswordSalt(String passwordSalt) {
        SystemConsts.passwordSalt = passwordSalt;
    }

    public void setPageCurrent(String pageCurrent) {
        SystemConsts.pageCurrent = Integer.parseInt(pageCurrent);
    }

    public void setPageSize(String pageSize) {
        SystemConsts.pageSize = Integer.parseInt(pageSize);
    }

    public void setAscend(int ascend) {
        SystemConsts.ascend = ascend;
    }

    public void setDescend(int descend) {
        SystemConsts.descend = descend;
    }
}
