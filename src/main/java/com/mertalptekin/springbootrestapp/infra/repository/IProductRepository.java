package com.mertalptekin.springbootrestapp.infra.repository;

import com.mertalptekin.springbootrestapp.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    // Fiyat aralığına göre ürünleri getiren metot
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
