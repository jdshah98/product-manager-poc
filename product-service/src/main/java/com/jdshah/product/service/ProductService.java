package com.jdshah.product.service;

import com.jdshah.product.dto.ProductRequest;
import com.jdshah.product.dto.ProductResponse;
import com.jdshah.product.model.Product;
import com.jdshah.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product ->
            new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice())
        ).toList();
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
