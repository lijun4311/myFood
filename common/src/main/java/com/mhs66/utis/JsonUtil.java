package com.mhs66.utis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mhs66.config.ILogBase;
import com.mhs66.config.Jackson;

import java.text.SimpleDateFormat;

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
    public static <T> String obj2String(T obj) {
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

    public static <T> String obj2StringPretty(T obj) {
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


    public static <T> T string2Obj(String str, Class<T> clazz) {
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


    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
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


    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }
}
