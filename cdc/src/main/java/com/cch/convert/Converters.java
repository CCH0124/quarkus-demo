package com.cch.convert;

import org.apache.camel.Converter;
import org.apache.kafka.connect.data.Struct;

import com.cch.entity.Movie;

@Converter
public class Converters {
    @Converter
    public static Movie questionFromStruct(Struct struct) {
        return new Movie(struct.getString("id"), struct.getString("name"), struct.getString("director"), struct.getString("genre"));
    }
}
