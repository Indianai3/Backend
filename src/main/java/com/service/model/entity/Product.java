package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.model.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private String productId;
    private ProductType productType;
    private String title;
    private Integer price;
    private String description;
    private String category;
    private List<Image> images;
    private Double rating;
    private String Gender;
    @JsonIgnore
    private String fbUid;

    // for AI products
    private String prompt;
    private Image image;

    // for Collections

}
