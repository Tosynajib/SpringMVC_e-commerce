package com.example.springmvcpractice.repositories;

import com.example.springmvcpractice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositories extends JpaRepository<Order, Long> {
}
