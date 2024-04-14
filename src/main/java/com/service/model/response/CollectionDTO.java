package com.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.model.entity.Collection;
import com.service.model.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionDTO {
    private String collectionId;
    private String name;
    private String description;
    private boolean active;
    private List<Product> products;
    private Long createdAt;

    public CollectionDTO(Collection collection) {
        this.collectionId = collection.getCollectionId();
        this.name = collection.getName();
        this.description = collection.getName();
        this.active = collection.isActive();
        this.createdAt = collection.getCreatedAt();
    }
}
