package com.service.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedImage {
    private String docId;
    private String base64Image;
    private HashMap<String, Object> metaData;
    private Long createdAt;
}
