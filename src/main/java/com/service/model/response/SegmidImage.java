package com.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmidImage {

    private String docId;
    private byte[] imageBytes;
    private HashMap<String, Object> metaData;
}
