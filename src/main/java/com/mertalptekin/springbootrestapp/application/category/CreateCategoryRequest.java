package com.mertalptekin.springbootrestapp.application.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// class yerine dtolarda record tercih ederiz. bunu sebebi classlarda settlar ile değer değişebilir.
// class mutable bir yapıya sahip. entityler için ideal.
// record immutable'dır. request ara bir işlemde değiştirilemez. Clientdan gönderildiği şekli ile kalmalıdır

public record CreateCategoryRequest(
        @JsonProperty("categoryName")
        @NotBlank(message = "Category name must not be blank")
        @Size(max = 50, message = "Category name must not exceed 50 characters")
        String name
) {
}
