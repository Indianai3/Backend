package com.service.model.request;

import com.service.model.AiModel;
import com.service.model.PrintType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorRequest {
    private AiModel aiModel;
    private String prompt;
    private String gender;
    private String category;
    private PrintType printType;
}
