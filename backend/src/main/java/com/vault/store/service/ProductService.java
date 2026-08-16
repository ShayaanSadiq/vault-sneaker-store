package com.vault.store.service;

import com.vault.store.domain.Product;
import com.vault.store.dto.ProductResponse;
import com.vault.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> listProducts(String category) {
        List<Product> products = category == null || category.isBlank() || "all".equalsIgnoreCase(category)
                ? productRepository.findAll()
                : productRepository.findByCategoryIgnoreCase(category);
        return products.stream().map(this::toResponse).toList();
    }

    public ProductResponse getProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        return toResponse(product);
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .category(product.getCategory())
                .badge(product.getBadge())
                .description(product.getDescription())
                .image(product.getImage())
                .build();
    }
}
