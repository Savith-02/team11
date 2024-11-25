package com.example.team11.Repository;

import com.example.team11.Entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    //*****************************
    Optional<Cart> findByUserId(Long userId);

    
    //*****************************
}

