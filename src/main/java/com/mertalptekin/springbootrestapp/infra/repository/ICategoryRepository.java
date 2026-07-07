package com.mertalptekin.springbootrestapp.infra.repository;

import com.mertalptekin.springbootrestapp.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    // JPQL formatında custom query yazımı için kullanılabilir.


    // NamedParameter :categoryId
    // Ben kategoriyi idsine göre çekerken products alanını da fetch etmesini istiyorum.
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :categoryId")
    Optional<Category> findWithProductsByCategoryId(@Param("categoryId") Integer categoryId);
}
