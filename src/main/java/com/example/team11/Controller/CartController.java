package com.example.team11.Controller;

import com.example.team11.DTO.CartDTO;
import com.example.team11.DTO.ProductQuantityDTO;
import com.example.team11.Service.CartService;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//*****************************
// import com.example.team11.Entity.CartItem;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//*****************************

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {
    //*****************************
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    //*****************************
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Create a new Cart
    @GetMapping("/create/{userId}")
    public Long createCart(@PathVariable Long userId) {
        cartService.createCart(userId);
        Long cartId = cartService.getCartIdByUserId(userId);
        return cartId;
    }

    // Delete a Cart by ID
    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }

    // Get a Cart by ID with its items
    @GetMapping("/{id}")
    public CartDTO getCart(@PathVariable Long id) {
        return cartService.getCartById(id); // Delegates to CartService
    }
    
    //*****************************
    // Get Cart Items by Cart ID
    @GetMapping("/{userId}/items")
    public List<ProductQuantityDTO> getCartItems(@PathVariable Long userId) {
        
       logger.info("Fetching cart items for User ID: {}", userId);
       Long cartId = cartService.getCartIdByUserId(userId);
       logger.info("Cart ID of User ID: {}", cartId);
       return cartService.getCartItemsByCartId(cartId);
    }

    @GetMapping("/userId/{userId}")
    public Long getCartIdByUserId(@PathVariable Long userId) {
        logger.info("Fetching cart ID for User ID: {}", userId);
        return cartService.getCartIdByUserId(userId);
    }
    @PostMapping("/{cartId}/checkout")
    public void checkoutCart(@PathVariable Long cartId) {
        logger.info("Cart Controller: Checking out cart ID: {}", cartId);
        cartService.checkoutCart(cartId);
    }
    //*****************************
}
