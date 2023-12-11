package com.example.springmvcpractice.repositories;

import com.example.springmvcpractice.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepositories extends JpaRepository<Users, Long> {

    Optional<Users> findUserByUsername(String username);
}
