package com.mertalptekin.springbootrestapp.application.category;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    @JsonProperty(value = "productId")
    private Integer id;

    @JsonProperty(value = "productName")
    private String name;

    @JsonProperty(value = "productPrice")
    private BigDecimal price;

}
