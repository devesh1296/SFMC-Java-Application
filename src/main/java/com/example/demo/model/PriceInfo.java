package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceInfo {

    private PriceDisplayCodes priceDisplayCodes;
    private CurrentPrice currentPrice;
    private UnitPrice unitPrice;
}
