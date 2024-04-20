package com.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.model.entity.DetailedProduct;
import com.service.model.entity.UserSession;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSessionDTO {
    private Bucket favouriteEntries;
    private Bucket cartEntries;

    public UserSessionDTO(UserSession userSession) {
        if (Objects.nonNull(userSession)) {
            favouriteEntries = new Bucket(userSession.getFavourites());
            cartEntries = new Bucket(userSession.getCartEntries());
        }
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Bucket {
        private List<DetailedProduct> entries = new ArrayList<>();
        private int bucketSize = 0;

        public Bucket(Map<String, DetailedProduct> entryMap) {
            if(!Objects.isNull(entryMap)) {
                entries = entryMap.values().stream().toList();
                bucketSize = entries.size();
            }
        }
    }
}
