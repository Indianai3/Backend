package com.service.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.model.AiModel;
import com.service.model.PrintType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneratorRequest {
    @JsonIgnore
    private AiModel aiModel;
    private String prompt;
    private String gender;
    private String category;
    private PrintType printType;
}
