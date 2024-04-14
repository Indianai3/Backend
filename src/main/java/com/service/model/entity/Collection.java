package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {
    private String collectionId;
    private String name;
    private String description;
    private boolean active;
    private List<String> productIds;
    private Long createdAt;
}
