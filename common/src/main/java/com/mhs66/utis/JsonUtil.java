package com.mhs66.utis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhs66.config.ILogBase;
import com.mhs66.config.Jackson;

/**
 * description:
 * json工具类
 *
 * @author 76442
 * @date 2020-07-15 21:04
 */


public class JsonUtil implements ILogBase {

    private static final ObjectMapper OBJECT_MAPPER = Jackson.getObjectMapper();



    /**
     * 对象序列化为json字符
     *
     * @param obj obj
     * @param <T> 泛型
     * @return string
     */
    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * 对象序列化为json字符 格式化
     * @param obj 对象
     * @return str
     */
    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * json字符转对象
     * @param str json
     * @param clazz 对象类型
     * @return T
     */
    public static <T> T stringToObj(String str, Class<T> clazz) {
        if (IStringUtil.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     *  json字符转对象
     * 使用TypeReference可以明确的指定反序列化的类型
     * @param str json
     * @param typeReference 指定类型
     * @return  typeReference 指定类型
     */
    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if (IStringUtil.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str, typeReference);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     *  json 字符转集合
     * @param str json
     * @param collectionClass 集合类型
     * @param elementClasses 集合元素类型
     * @return T
     */
    public static <T> T stringToObj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }
}
