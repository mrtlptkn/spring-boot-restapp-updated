package com.mertalptekin.springbootrestapp.application.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(

        @JsonProperty("categoryId")
        @NotNull(message = "Id must not be blank") // String, Integer, Long gibi değerler null olmamalı
        Integer id,

        @JsonProperty("categoryName")
        @NotBlank(message = "Category name must not be blank") // null or " "
        @NotEmpty(message = "Category name must not be empty") // null or ""
        @Size(max = 10, message = "Category name must not exceed 10 characters")
        String name
) {
}
