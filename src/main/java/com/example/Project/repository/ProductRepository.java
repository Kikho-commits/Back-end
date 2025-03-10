package com.example.Project.repository;

import com.example.Project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByDeletedFalseAndEmail(String email, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.deleted = false AND p.email = :email " +
            "AND (:searchTerm IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.supplierName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Product> findByEmailAndQuery(@Param("email") String email, @Param("searchTerm") String searchTerm, Pageable pageable);

    List<Product> findByEmail(String email);
}
