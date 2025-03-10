package com.example.Project.service;

import com.example.Project.dto.ProductRequest;
import com.example.Project.dto.ProductResponse;
import com.example.Project.dto.SummaryResponse;
import com.example.Project.entity.Product;
import com.example.Project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse getAllProducts(int page, int size, String searchTerm, String sortBy, Sort.Direction sortDirection, String email) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Product> productPage = productRepository.findByEmailAndQuery(email, searchTerm, pageable);

        return new ProductResponse(
                productPage.getContent(),
                productPage.getTotalPages(),
                productPage.getTotalElements()
        );
    }

    public Product addProduct(Product product) {
        product.setDeleted(false);
        product.setCreatedTime(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductRequest request, String email) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            if (!product.getEmail().equals(email)) {
                throw new RuntimeException("Unauthorized to update this product");
            }
            product.setProductName(request.getProductName());
            product.setQuantity(request.getQuantity());
            product.setPrice(request.getPrice());
            product.setSupplierName(request.getSupplierName());
            product.setUpdatedTime(LocalDateTime.now());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long productId, String email) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (!product.getEmail().equals(email)) {
                throw new RuntimeException("Unauthorized to delete this product");
            }
            product.setDeleted(true);
            product.setDeletedTime(LocalDateTime.now());
            productRepository.save(product);
        } else {
            return ;
        }
    }
    public SummaryResponse getSummary(String email) {
        List<Product> products = productRepository.findByEmail(email);

        long totalProducts = 0;
        double totalValue = 0.0;

        for (Product product : products) {
            if (product.getQuantity() > 0 && !product.getDeleted()) {
                totalProducts+=product.getQuantity();
                totalValue += product.getPrice() * product.getQuantity();
            }
        }

        return new SummaryResponse(totalProducts, totalValue);
    }
}
