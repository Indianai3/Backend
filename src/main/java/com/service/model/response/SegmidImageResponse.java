package com.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmidImageResponse {

    private String docId;
    private InputStreamResource imageStream;
    private HashMap<String, Object> metaData;
}