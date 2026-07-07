package com.mertalptekin.springbootrestapp.application.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(

        @JsonProperty("categoryId")
        @NotNull(message = "Id must not be blank")
        Integer id,

        @JsonProperty("categoryName")
        @NotBlank(message = "Category name must not be blank")
        @Size(max = 50, message = "Category name must not exceed 50 characters")
        String name
) {
}
