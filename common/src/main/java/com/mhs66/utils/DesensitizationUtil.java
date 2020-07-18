package com.mhs66.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * description:
 * 通用脱敏工具类
 *
 * @author 76442
 * @date 2020-07-18 16:24
 */
@SuppressWarnings({"ALL", "AlibabaUndefineMagicConstant"})
public class DesensitizationUtil {

    private static final int SIZE = 6;
    private static final String SYMBOL = "*";

    /**
     * 通用脱敏方法
     *
     * @param value 入参
     * @return 出参 123***456
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static String commonDisplay(String value) {
        if (null == value || "".equals(value)) {
            return value;
        }
        int len = value.length();
        int pamaone = len / 2;
        int pamatwo = pamaone - 1;
        int pamathree = len % 2;
        StringBuilder stringBuilder = new StringBuilder();
        //noinspection AlibabaUndefineMagicConstant
        if (len <= 2) {
            if (pamathree == 1) {
                return SYMBOL;
            }
            stringBuilder.append(SYMBOL);
            stringBuilder.append(value.charAt(len - 1));
        } else {
            if (pamatwo <= 0) {
                stringBuilder.append(value.substring(0, 1));
                stringBuilder.append(SYMBOL);
                stringBuilder.append(value.substring(len - 1, len));

            } else //noinspection AlibabaUndefineMagicConstant
                if (pamatwo >= SIZE / 2 && SIZE + 1 != len) {
                    int pamafive = (len - SIZE) / 2;
                    stringBuilder.append(value.substring(0, pamafive));
                    for (int i = 0; i < SIZE; i++) {
                        stringBuilder.append(SYMBOL);
                    }
                    //noinspection AlibabaUndefineMagicConstant,AlibabaUndefineMagicConstant
                    if ((pamathree == 0 && SIZE / 2 == 0) || (pamathree != 0 && SIZE % 2 != 0)) {
                        stringBuilder.append(value.substring(len - pamafive, len));
                    } else {
                        stringBuilder.append(value.substring(len - (pamafive + 1), len));
                    }
                } else {
                    int pamafour = len - 2;
                    stringBuilder.append(value.substring(0, 1));
                    for (int i = 0; i < pamafour; i++) {
                        stringBuilder.append(SYMBOL);
                    }
                    stringBuilder.append(value.substring(len - 1, len));
                }
        }
        return stringBuilder.toString();
    }


    /**
     * 姓名脱敏
     *
     * @param userName 姓名
     * @param index    第几位开始
     * @return name
     */
    public static String hideName(String userName, int index) {
        if (StringUtils.isBlank(userName)) {
            return "";
        }
        String name = StringUtils.left(userName, index);
        return StringUtils.rightPad(name, StringUtils.length(userName), "*");
    }

    /**
     * 身份证脱敏
     *
     * @param cardNo 身份证号
     * @param index  第几位开始
     * @param end    第几位结束
     * @return cardno
     */
    public static String hideCerCardNum(String cardNo, int index, int end) {
        if (StringUtils.isBlank(cardNo)) {
            return "";
        }
        return StringUtils.left(cardNo, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNo, end), StringUtils.length(cardNo), "*"), "***"));
    }

    /**
     * 电话脱敏
     *
     * @param phoneNum 手机号
     * @param end      第几位结束
     * @return phonenum
     */
    public static String hidePhone(String phoneNum, int end) {
        if (StringUtils.isBlank(phoneNum)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(phoneNum, end), StringUtils.length(phoneNum), "*");
    }

    /**
     * 邮箱脱敏
     *
     * @param email 邮箱
     * @return 邮箱
     */
    public static String email(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }

    }

    /**
     * 银行卡号脱敏
     *
     * @param cardNum 卡号
     * @return 卡号
     */
    public static String bankCard(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, 4).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
    }

}
