package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String addressLine;
    private String landmark;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String phoneNumber;
    private String name;
}
