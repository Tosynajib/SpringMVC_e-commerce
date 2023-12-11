package com.example.springmvcpractice.models;

import com.example.springmvcpractice.dtos.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categories;
    private BigDecimal price;
    private String productName;
    private Long quantity;
    private String image;

    public Product(ProductDto productDto){
        this.categories=productDto.getCategory();
        this.productName=productDto.getName();
        this.price=productDto.getPrice();
        this.quantity=productDto.getQuantity();
        this.image=productDto.getImage();
    }

}

