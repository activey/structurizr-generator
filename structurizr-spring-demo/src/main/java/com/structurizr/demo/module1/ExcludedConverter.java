package com.structurizr.demo.module1;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExcludedConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        return null;
    }
}
