package com.example.team11.Controller;

import com.example.team11.DTO.AddProductToCartDTO;
// import com.example.team11.DTO.ProductQuantityDTO;
import com.example.team11.Service.CartItemService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.team11.Entity.Product;
import com.example.team11.Service.ProductService;
import com.example.team11.DTO.ProductDTO;
import com.example.team11.DTO.CartItemDTO;
// import com.example.team11.Entity.CartItem;
@CrossOrigin
@RestController
@RequestMapping("/api/cart/{cartId}/items")
public class CartItemController {
    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);
    private final CartItemService cartItemService;
    private final ProductService productService;

    public CartItemController(CartItemService cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    // Add a CartItem
    @PostMapping
    public void addCartItem(@PathVariable Long cartId, @RequestBody AddProductToCartDTO addProductToCartDTO) {
        logger.info("Inside addCartItem, Adding product to cart ID:{}", addProductToCartDTO.getProductId(), cartId);
        logger.info("Quantity: {}", addProductToCartDTO.getQuantity());
        // ProductQuantityDTO productQuantityDTO = new ProductQuantityDTO();
        ProductDTO productDTO = productService.getProductById(addProductToCartDTO.getProductId());
        Product product = productService.convertToEntity(productDTO);
        CartItemDTO cartItem = new CartItemDTO();
        cartItem.setProductName(product.getProductName());
        cartItem.setPrice(product.getPrice());
        cartItem.setSupplierId(product.getSupplierId());
        cartItem.setCategory(product.getCategory());    
        cartItem.setQuantity(addProductToCartDTO.getQuantity());
        cartItemService.addCartItem(cartId, cartItem);
    }
/*
    // Update a CartItem
    @PutMapping("/{itemId}")
    public void updateCartItem(@PathVariable Long cartId, @PathVariable Long itemId, @RequestBody CartItemDTO cartItemDTO) {
        cartItemService.updateCartItem(cartId, itemId, cartItemDTO); // Delegates to CartItemService
    }
*/
    // Delete a CartItem
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

