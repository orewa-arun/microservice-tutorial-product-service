package com.microservice.productService.service;

import com.microservice.productService.dto.ProductRequest;
import com.microservice.productService.dto.ProductResponse;
import com.microservice.productService.model.Product;
import com.microservice.productService.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).category(productRequest.getCategory()).price(productRequest.getPrice()).build();
        productRepository.save(product);
        log.info("Product {} is created", product.getId());
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription()).category(product.getCategory()).price(product.getPrice()).build();
    }
}
