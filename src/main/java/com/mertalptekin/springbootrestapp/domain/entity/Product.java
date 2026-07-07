package com.mertalptekin.springbootrestapp.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Setter
@Getter
// boş constructor ile çalışması için
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    // PK ilişki - FK ilişki
    @ManyToOne
    @JoinColumn(name = "category_id") // ürün tablosunda category_id ile ilkişki kurulacak
    private Category category;


}
