package com.mertalptekin.springbootrestapp.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_prices")
@Data
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "old_price", nullable = false)
    private BigDecimal oldPrice;

    @Column(name = "new_price", nullable = false)
    private BigDecimal newPrice;

    @Column(name="changed_at", nullable = false)
    private LocalDateTime changedAt; // Fiyat değişim zamanını epoch formatında saklayabilir

    // EAGER, LEFT JOIN ile ilişkili veriyi hemen yükler
    // LAZY, ilişkili veriyi ihtiyaç duyulduğunda yükler

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false) // JOIN işlemi için product_id kolonunu kullan
    private Product product; // Navigation property to Product entity
    // Product Table FK olarak product_id kolonuna sahip olacak


}
