package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.annotations.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedProduct {
    @NotNull("productId cannot be null")
    private String productId;
    private Product product;
    private ProductMeta productMeta;
    private Integer quantity;
}
