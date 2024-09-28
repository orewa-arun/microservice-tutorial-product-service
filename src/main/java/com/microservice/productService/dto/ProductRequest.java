package com.microservice.productService.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
}
