package com.example.Project.service;

import com.example.Project.dto.ProductRequest;
import com.example.Project.dto.ProductResponse;
import com.example.Project.entity.Product;
import org.springframework.data.domain.Sort;
import com.example.Project.dto.SummaryResponse;

public interface ProductService {

    ProductResponse getAllProducts(int page, int size, String searchTerm, String sortBy, Sort.Direction sortDirection, String email);
    Product addProduct(Product product);
    Product updateProduct(Long productId, ProductRequest request, String email);
    void deleteProduct(Long productId, String email);
    public SummaryResponse getSummary(String email);

}
