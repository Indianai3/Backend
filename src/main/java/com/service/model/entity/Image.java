package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
    private String imageId;
    private String imageUrl;
    private Long createdAt;
    @JsonIgnore
    private String fbUid;
    @JsonIgnore
    private HashMap<String, Object> metaData;
}
