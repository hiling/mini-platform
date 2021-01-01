package com.github.hiling.common.utils.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/26/2018.
 * 适用于jdk1.8及以上
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeString(DATE_FORMATTER.format(value));
    }
}