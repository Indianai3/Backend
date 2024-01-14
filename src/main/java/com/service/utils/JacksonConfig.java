package com.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Register the custom serializer for InputStreamResource
        objectMapper.registerModule(new SimpleModule().addSerializer(InputStreamResource.class,
                new InputStreamResourceSerializer()));
        return objectMapper;
    }
}
