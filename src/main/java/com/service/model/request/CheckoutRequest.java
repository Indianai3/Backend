package com.service.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.model.entity.DetailedProduct;
import com.service.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutRequest {
    private List<DetailedProduct> products;
    private Address address;
}
