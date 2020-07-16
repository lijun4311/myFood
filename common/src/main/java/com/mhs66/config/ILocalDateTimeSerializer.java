package com.mhs66.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *description:
 *JSON序列化时间处理类
 *@author 76442
 *@date 2020-07-15 21:09
 */
public class ILocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public static final ILocalDateTimeSerializer INSTANCE = new ILocalDateTimeSerializer();

    private ILocalDateTimeSerializer() {
        super();
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //时间戳处理函数
        long timestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        jsonGenerator.writeNumber(timestamp);
    }
}