package com.example.team11.Service;

import com.example.team11.DTO.CartDTO;
import com.example.team11.DTO.CartItemDTO;
import com.example.team11.Entity.Cart;
import com.example.team11.Entity.CartItem;
import com.example.team11.Repository.CartRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
//*****************************
import com.example.team11.Repository.CartItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//*****************************
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    //*****************************
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
    //*****************************

    // Method to create a new Cart
    public CartDTO createCart() {
        Cart cart = new Cart();
        cart.setTotalPrice(0.0); // Initialize total price as 0
        cart = cartRepository.save(cart); // Save and return the new Cart
        return convertToDTO(cart); // Convert and return the CartDTO
    }



    
    // Method to delete a Cart by ID
    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
        cartRepository.delete(cart); // Delete the Cart
    }

    
// Conversion methods between Entity and DTO
private CartDTO convertToDTO(Cart cart) {
    CartDTO cartDTO = new CartDTO();
    cartDTO.setId(cart.getId());
    cartDTO.setTotalPrice(cart.getTotalPrice());

    // Convert CartItem entities to CartItemDTOs
    List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
            .map(item -> {
                CartItemDTO cartItemDTO = new CartItemDTO();
                cartItemDTO.setId(item.getId());
                cartItemDTO.setProductName(item.getProductName());
                cartItemDTO.setQuantity(item.getQuantity());
                cartItemDTO.setPrice(item.getPrice());
                return cartItemDTO;
            })
            .collect(Collectors.toList());

    cartDTO.setItems(cartItemDTOs); // Set the list of CartItemDTOs
    return cartDTO;
}




// Method to retrieve a Cart by its ID
public CartDTO getCartById(Long id) {
    Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart not found")); // Throw an exception if cart not found
    return convertToDTO(cart); // Convert the Cart entity to CartDTO and return it
}

//*****************************
  // Method to get cart items by cart ID
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        logger.info("Fetching cart items for Cart ID: {}", cartId);
        return cartItemRepository.findByCartId(cartId);
    }

       // In CartService.java
   public Long getCartIdByUserId(Long userId) {
       logger.info("Fetching cart ID for User ID: {}", userId);
       Cart cart = cartRepository.findByUserId(userId)
               .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));
       return cart.getId();
   }
//*****************************
}

