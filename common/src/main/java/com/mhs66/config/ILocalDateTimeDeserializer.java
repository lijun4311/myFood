package com.mhs66.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *description:
 * 我的json序列化时间处理类
 *@author 76442
 *@date 2020-07-15 21:09
 */
public class ILocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final ILocalDateTimeDeserializer INSTANCE = new ILocalDateTimeDeserializer();

    private ILocalDateTimeDeserializer() {
        super();
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        long timestamp = jsonParser.getValueAsLong();
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

    }
}
