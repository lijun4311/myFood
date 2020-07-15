package com.mhs66.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.mhs66.utis.IDateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    private static final ObjectMapper objectMapper;
    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, ILocalDateTimeSerializer.INSTANCE);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(IDateUtil.DAY_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(IDateUtil.TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, ILocalDateTimeDeserializer.INSTANCE);
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(IDateUtil.DAY_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(IDateUtil.TIME_FORMAT)));
        objectMapper = new ObjectMapper();
        //注入时间处理模块
        objectMapper.registerModule(javaTimeModule);
        //序列化规则过滤null
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper() {
        //序列化时间处理模块注入
        return getObjectMapper();
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
