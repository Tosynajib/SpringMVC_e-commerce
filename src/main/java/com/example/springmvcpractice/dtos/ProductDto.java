package com.example.springmvcpractice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Long quantity;
    private String category;
    private BigDecimal price;
    private String image;
}
