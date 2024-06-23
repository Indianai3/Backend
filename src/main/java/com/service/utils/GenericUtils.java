package com.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class GenericUtils {
    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public String getUuid() {
        return UUID.randomUUID().toString();
    }

}
