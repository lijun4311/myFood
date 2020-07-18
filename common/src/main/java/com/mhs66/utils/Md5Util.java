package com.mhs66.utils;

import com.mhs66.config.ILogBase;
import com.mhs66.consts.PageConsts;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * description:
 * MD5加密工具
 *
 * @author 76442
 * @date 2020-07-15 20:59
 */
public class Md5Util implements ILogBase {


    /**
     * 字节码转字符
     *
     * @param b 字节码
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    /**
     * 对字符进行转码换算
     *
     * @param b 输入字符
     * @return 返回16进制字符
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * 返回大写MD5 加密码
     *
     * @param origin 输入字符串
     * @return 大写32位md5 编码
     */
    private static String md5Encode(String origin) {
        if (StringUtils.isNotBlank(origin)) {

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                origin = byteArrayToHexString(md.digest(origin.getBytes(StandardCharsets.UTF_8)));
            } catch (NoSuchAlgorithmException e) {
                log.error("MD5 算法工具获取失败 excepton:", e);
            }
            return origin.toUpperCase();
        } else {
            throw new NullPointerException();
        }

    }

    /**
     * 读取配置文件 进行md5 加密
     *
     * @param origin 原字符
     * @return 返回 编码字符
     */
    public static String md5EncodeUtf8(String origin) {
        origin = origin + PageConsts.getPasswordSalt();
        return md5Encode(origin);
    }

    /**
     * 16进制数组
     */
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


}
