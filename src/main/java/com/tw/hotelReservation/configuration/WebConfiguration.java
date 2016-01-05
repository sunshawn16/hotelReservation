package com.tw.hotelReservation.configuration;

import com.tw.hotelReservation.converter.LocalDateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    private static final String DATE_PATTERN = "yyyy/MM/dd";

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter(DATE_PATTERN));
    }
}
