package com.service.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveEvent {
    private String docId;
    private String imageId;
    private Object request;
    private String requestedUserUid;
    private HashMap generationMetaData;
    private Long createdAt;
}
