package com.tw.hotelReservation.converter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.joda.time.LocalDate.parse;
import static org.joda.time.format.DateTimeFormat.forPattern;

public class LocalDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter formatter;

    public LocalDateConverter(String pattern) {
        this.formatter = forPattern(pattern);
    }

    @Override
    public LocalDate convert(String source) {
        return isNullOrEmpty(source) ? null : parse(source, formatter);
    }
}
