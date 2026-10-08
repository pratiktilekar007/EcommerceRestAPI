package com.app.ecommerce;

import com.app.ecommerce.dto.ProductDto;
import com.app.ecommerce.entity.Product;
import com.app.ecommerce.exception.ResourceNotFoundException;
import com.app.ecommerce.repository.ProductRepository;
import com.app.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = Product.builder()
                .name("Test Product")
                .description("A great product")
                .price(new BigDecimal("99.99"))
                .stockQuantity(10)
                .category("Electronics")
                .build();
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));

        List<ProductDto.Response> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Product");
    }

    @Test
    void getById_shouldReturnProduct_whenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        ProductDto.Response result = productService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("99.99"));
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnProduct() {
        ProductDto.Request request = new ProductDto.Request();
        request.setName("New Product");
        request.setPrice(new BigDecimal("49.99"));
        request.setStockQuantity(5);
        request.setCategory("Books");

        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        ProductDto.Response result = productService.create(request);

        assertThat(result).isNotNull();
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void delete_shouldRemoveProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        productService.delete(1L);

        verify(productRepository, times(1)).delete(sampleProduct);
    }
}
