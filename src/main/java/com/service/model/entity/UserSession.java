package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSession {
    private Map<String, DetailedProduct> favourites = new HashMap<>();
    private Map<String, DetailedProduct> cartEntries = new HashMap<>();
}
