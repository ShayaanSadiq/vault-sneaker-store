package com.vault.store.service;

import com.vault.store.domain.Product;
import com.vault.store.dto.ProductResponse;
import com.vault.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void listProductsUsesFindAllWhenCategoryIsMissing() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct()));

        List<ProductResponse> result = productService.listProducts(null);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Apex Infrared");
        verify(productRepository).findAll();
    }

    @Test
    void listProductsFiltersByCategory() {
        when(productRepository.findByCategoryIgnoreCase("Running")).thenReturn(List.of(sampleProduct()));

        List<ProductResponse> result = productService.listProducts("Running");

        assertThat(result).extracting(ProductResponse::getCategory).containsExactly("Running");
        verify(productRepository).findByCategoryIgnoreCase("Running");
    }

    @Test
    void getProductThrowsWhenMissing() {
        when(productRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProduct("missing"))
                .isInstanceOf(java.util.NoSuchElementException.class)
                .hasMessage("Product not found");
    }

    private Product sampleProduct() {
        return Product.builder()
                .id("apex-infrared")
                .name("Apex Infrared")
                .brand("VAULT")
                .price(189)
                .category("Running")
                .badge("New Drop")
                .description("Daily trainer")
                .image("https://example.com/apex.jpg")
                .build();
    }
}
