package com.example.team11.Repository;

import com.example.team11.Entity.CartItem;
//*****************************
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    //*****************************
    List<CartItem> findByCartId(Long cartId);
    
    //*****************************
}

