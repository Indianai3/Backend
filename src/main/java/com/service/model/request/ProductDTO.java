package com.service.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.model.ProductType;
import com.service.model.entity.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
    private String productId;
    private ProductType productType;
    private String title;
    private Integer price;
    private String description;
    private String category;
    private List<String> imageIds;
    private Double rating;
    private String Gender;
    @JsonIgnore
    private String fbUid;
    private String prompt;
    private Image image;
}
