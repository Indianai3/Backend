package com.service.service.product;

import com.service.dao.FirebaseDao;
import com.service.model.entity.Collection;
import com.service.model.request.CollectionDTO;
import com.service.utils.Constants;
import com.service.utils.GenericUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CollectionService {
    private final FirebaseDao firebaseDao;
    private final ProductService productService;
    @Getter
    private List<CollectionDTO> activeCollections;

    public CollectionService(FirebaseDao firebaseDao, ProductService productService) {
      this.firebaseDao = firebaseDao;
      this.productService = productService;
      refreshCollections();
    }

    public void refreshCollections() {
        List<Collection> collections = firebaseDao.getAllDocuments(Constants.COLLECTIONS, Collection.class);
        if (!Objects.isNull(collections) && !collections.isEmpty()) {
            collections = collections.stream()
                    .filter(Collection::isActive)
                    .toList();
            setActiveCollectionDtosFromCollections(collections);
        } else {
            activeCollections = new ArrayList<>();
        }
    }

    public CollectionDTO addCollection(String fbUid, Collection collection) {
        validateCollectionRequest(collection);
        if(Strings.isBlank(collection.getCollectionId())) {
            collection.setCollectionId(GenericUtils.getUuid());
        }
        collection.setCreatedAt(System.currentTimeMillis());
        firebaseDao.saveDocument(Constants.COLLECTIONS, collection.getCollectionId(), collection);
        return mapCollectionTODto(collection);
    }

    private void setActiveCollectionDtosFromCollections(List<Collection> collections) {
        activeCollections = collections.stream()
                .map(this::mapCollectionTODto)
                .collect(Collectors.toList());
    }

    private void validateCollectionRequest(Collection collection) {
        //todo: implement validation
    }

    private CollectionDTO mapCollectionTODto(Collection collection) {
        CollectionDTO collectionDto = new CollectionDTO(collection);
        if(Objects.nonNull(collection.getProductIds())) {
            collectionDto.setProducts(collection.getProductIds()
                    .stream()
                    .map(productService::getProduct)
                    .toList());
        }
        return collectionDto;
    }

}
