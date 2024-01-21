package com.service.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class GenericUtils {
    public String getUuid() {
        return UUID.randomUUID().toString();
    }

}
