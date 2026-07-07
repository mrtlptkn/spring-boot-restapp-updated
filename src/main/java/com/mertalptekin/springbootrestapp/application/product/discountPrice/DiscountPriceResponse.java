package com.mertalptekin.springbootrestapp.application.product.discountPrice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mertalptekin.springbootrestapp.application.product.IProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DiscountPriceResponse implements IProductResponse {

    @JsonProperty("productId")
    private Integer id;
    @JsonProperty("oldPrice")
    private BigDecimal oldPrice;

    @JsonProperty("newPrice")
    private BigDecimal newPrice;
}
