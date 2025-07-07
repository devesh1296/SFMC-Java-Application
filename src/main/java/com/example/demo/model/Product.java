package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private String id;
    private Integer numberOfReviews;
    private String availabilityStatus;
    private PriceInfo priceInfo;
    private String canonicalUrl;
}
