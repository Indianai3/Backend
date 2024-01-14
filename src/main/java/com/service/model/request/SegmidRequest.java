package com.service.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class SegmidRequest {
    private String prompt;
    private String negative_prompt = "ugly, blurry, poor quality, animated, water";
    private int samples = 1;
    private String scheduler= "UniPC";
    private int num_inference_steps = 50;
    private double guidance_scale = 12.5;
    private Long seed = 80854540L;
    private int img_width = 1024;
    private int img_height = 1024;
    private boolean base64 = false;
}