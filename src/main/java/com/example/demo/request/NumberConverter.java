package com.example.demo.request;

import com.example.demo.domain.Number;
import org.springframework.core.convert.converter.Converter;

public class NumberConverter implements Converter<String, Number> {

    @Override
    public Number convert(String value) {
        return Number.findByValue(Integer.parseInt(value));
    }
}
