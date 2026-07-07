package com.mertalptekin.springbootrestapp.application.product.discountPrice;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mertalptekin.springbootrestapp.application.product.IProductRequest;

import java.math.BigDecimal;

public record DiscountPriceRequest(
        @JsonProperty("productId")
        Integer id,
        @JsonProperty("newPrice")
        BigDecimal newPrice
) implements IProductRequest { }
