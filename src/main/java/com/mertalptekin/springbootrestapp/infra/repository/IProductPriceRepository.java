package com.mertalptekin.springbootrestapp.infra.repository;

import com.mertalptekin.springbootrestapp.domain.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
