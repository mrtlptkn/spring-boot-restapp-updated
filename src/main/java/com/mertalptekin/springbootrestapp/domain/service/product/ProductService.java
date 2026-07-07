package com.mertalptekin.springbootrestapp.domain.service.product;

import com.mertalptekin.springbootrestapp.domain.entity.Product;
import com.mertalptekin.springbootrestapp.infra.repository.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService  implements IProductService{

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public void addProduct(Product product) {
        // aynı üründen veritabanında yoksa kaydetder varsa update eder.
        this.productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {

        Optional<Product> existingProduct = this.productRepository.findById(product.getId());

        if(existingProduct.isEmpty()){
            throw new EntityNotFoundException("Product not found with id: " + product.getId());
        } else {
            this.productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        this.productRepository.deleteById(productId);
    }

    @Override
    public Product getProductById(Integer productId) {

        Optional<Product> product = this.productRepository.findById(productId);

        if(product.isEmpty()){
            throw new EntityNotFoundException("Product not found with id: " + productId);
        } else {
            return product.get();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
