package com.mhs66.enums;

/**
 * 操作状态
 *
 * @author silence
 */
public enum BusinessStatus {
    /**
     * 业务状态
     */
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    YES(0, "是"),
    NO(1, "否");


    public final Integer type;
    public final String value;

    BusinessStatus(Integer type, String value) {
        this.type = type;
        this.value = value;
    }


}
