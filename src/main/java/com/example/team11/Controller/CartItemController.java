package com.example.team11.Controller;

import com.example.team11.DTO.CartItemDTO;
import com.example.team11.Service.CartItemService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/cart/{cartId}/items")
public class CartItemController {
    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // Add a CartItem
    @PostMapping
    public void addCartItem(@PathVariable Long cartId, @RequestBody CartItemDTO cartItemDTO) {
        cartItemService.addCartItem(cartId, cartItemDTO); // Delegates to CartItemService
    }
/*
    // Update a CartItem
    @PutMapping("/{itemId}")
    public void updateCartItem(@PathVariable Long cartId, @PathVariable Long itemId, @RequestBody CartItemDTO cartItemDTO) {
        cartItemService.updateCartItem(cartId, itemId, cartItemDTO); // Delegates to CartItemService
    }
*/
    // Delete a CartItem
    @CrossOrigin(origins = "*")
    @DeleteMapping("/{itemId}")
    public void deleteCartItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        logger.info("Inside deleteCartItem, Deleting item ID:{} in cart Id: {}", itemId, cartId);
        cartItemService.deleteCartItem(cartId, itemId); // Delegates to CartItemService
    }

    // Update the quantity of a CartItem
    @PatchMapping("/{itemId}/quantity")
    public void updateCartItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestBody int newQuantity) {
        logger.info("Inside updateCartItemQuantity, Updating item ID:{} in cart Id: {}", itemId, cartId);
        cartItemService.updateCartItemQuantity(cartId, itemId, newQuantity);
    }

}

