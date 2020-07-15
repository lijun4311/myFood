package com.mhs66.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.mhs66.utis.IDateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * description:
 *
 * @author 76442
 * @date 2020-07-15 21:18
 */
@Configuration
public class Jackson {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, ILocalDateTimeSerializer.INSTANCE);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(IDateUtil.DAY_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(IDateUtil.TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, ILocalDateTimeDeserializer.INSTANCE);
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(IDateUtil.DAY_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(IDateUtil.TIME_FORMAT)));
        OBJECT_MAPPER = new ObjectMapper();
        //注入时间处理模块
        OBJECT_MAPPER.registerModule(javaTimeModule);
        //序列化规则过滤null
        //ALWAYS 默认
        //NON_DEFAULT 属性为默认值不序列化
        //NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
        //NON_NULL 属性为NULL 不序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //对象的所有字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(IDateUtil.STANDARD_FORMAT));
        //取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper() {
        //序列化时间处理模块注入
        return getObjectMapper();
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
