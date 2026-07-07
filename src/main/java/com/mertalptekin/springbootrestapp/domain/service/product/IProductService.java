package com.mertalptekin.springbootrestapp.domain.service.product;

import com.mertalptekin.springbootrestapp.domain.entity.Product;

import java.util.List;

public interface IProductService {

    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Integer productId);
    Product getProductById(Integer productId);
    List<Product> getAllProducts();
}
