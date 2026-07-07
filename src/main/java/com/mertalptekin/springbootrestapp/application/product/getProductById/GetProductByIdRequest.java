package com.mertalptekin.springbootrestapp.application.product.getProductById;

import com.mertalptekin.springbootrestapp.application.product.IProductRequest;

public record GetProductByIdRequest(Integer productId) implements IProductRequest {
}
