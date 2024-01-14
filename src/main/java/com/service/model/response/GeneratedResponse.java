package com.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.model.request.Generator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedResponse {
    private SegmidImageResponse image;
    private Generator request;
    @JsonIgnore
    private Long generatedAt;
}
