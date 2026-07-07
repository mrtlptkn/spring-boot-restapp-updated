package com.mertalptekin.springbootrestapp.application.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    @JsonProperty(value = "categoryId")
    private Integer id;

    @JsonProperty(value = "categoryName")
    private String name;

    @JsonProperty(value = "products")
    private List<ProductResponseDto> products;

}
