
package com.example.team11.Service;

import com.example.team11.DTO.CartItemDTO;
import com.example.team11.Entity.Cart;
import com.example.team11.Entity.CartItem;
// import com.example.team11.Repository.CartItemRepository;
import com.example.team11.Repository.CartRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import java.util.Optional;
@Service
public class CartItemService {
    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);
    private final CartRepository cartRepository;
    // @SuppressWarnings("unused")
    // private final CartItemRepository cartItemRepository; // Assume this is the CartItem repository

    public CartItemService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        // this.cartItemRepository = cartItemRepository;
    }

    public void addCartItem(Long cartId, CartItemDTO cartItemDTO) {
      Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        
        // Check if the CartItem already exists in the cart
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProductName().equals(cartItemDTO.getProductName()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            logger.info("Service: Inside addCartItem, CartItem already exists in the cart");
            // Update the quantity of the existing CartItem
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
        } else {
            logger.info("Service: Inside addCartItem, CartItem does not exist in the cart");
            // Create and populate the CartItem entity from the DTO
            CartItem cartItem = new CartItem();
            cartItem.setProductName(cartItemDTO.getProductName());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setPrice(cartItemDTO.getPrice());
            cartItem.setSupplierId(cartItemDTO.getSupplierId());
            cartItem.setCategory(cartItemDTO.getCategory());
            cartItem.setCart(cart);  // Associate with the Cart

            // Add the item to the Cart
            cart.getItems().add(cartItem);
        }

        // Save the updated cart
        cartRepository.save(cart);
    
    }
    
/*
    // Update a CartItem
    public void updateCartItem(Long cartId, Long itemId, CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setProductName(cartItemDTO.getProductName());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());

        // Update total price
        updateTotalPrice(cart);

        // Save the updated cart
        cartRepository.save(cart);
    }
*/
    // Delete a CartItem
    @Transactional 
    public void deleteCartItem(Long cartId, Long itemId) {
        logger.info("Service: Inside deleteCartItem, Deleting item ID:{} in cart Id: {}", itemId, cartId);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getId().equals(itemId)); // Remove item by ID
        
        // Update total price
        // updateTotalPrice(cart);

        // Save the updated cart
        cartRepository.save(cart);
    }

    // Helper method to update the total price of the cart
    private void updateTotalPrice(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }

    public void updateCartItemQuantity(Long cartId, Long itemId, int newQuantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
    
        cartItem.setQuantity(newQuantity);
    
        // Update total price of the cart
        updateTotalPrice(cart);
    
        // Save the updated cart
        cartRepository.save(cart);
    }
    

}
