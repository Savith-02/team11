package com.example.team11.Service;

import com.example.team11.DTO.CartDTO;
import com.example.team11.DTO.ProductQuantityDTO;
import com.example.team11.Entity.Cart;
import com.example.team11.Entity.CartItem;
import com.example.team11.Repository.CartRepository;
import java.util.List;
import java.util.stream.Collectors;
import com.example.team11.Entity.User;
import com.example.team11.Repository.UserRepository;
import org.springframework.stereotype.Service;
//*****************************
import com.example.team11.Repository.CartItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.team11.Entity.Product;
// import com.example.team11.Service.ProductService;
// import com.example.team11.DTO.ProductDTO;
import java.util.ArrayList;
//*****************************
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    // private final ProductService productService;
    //*****************************
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
            // this.productService = productService;
    }
    //*****************************

    // Method to create a new Cart
    public CartDTO createCart(Long userId) {
        Cart cart = new Cart();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        cart.setUser(user);
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
    // cartDTO.setId(cart.getId());
    // cartDTO.setTotalPrice(cart.getTotalPrice());

    // Convert CartItem entities to CartItemDTOs
    List<ProductQuantityDTO> productQuantityDTOs = cart.getItems().stream()
            .map(item -> {
                // Product product = item.getProduct();
                ProductQuantityDTO productQuantityDTO = new ProductQuantityDTO();
                productQuantityDTO.setProduct(item);
                productQuantityDTO.setQuantity(item.getQuantity());
                return productQuantityDTO;
            })
            .collect(Collectors.toList());

    cartDTO.setItems(productQuantityDTOs); // Set the list of ProductQuantityDTOs
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
    public List<ProductQuantityDTO> getCartItemsByCartId(Long cartId) {
        logger.info("Fetching cart items for Cart ID: {}", cartId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        List<ProductQuantityDTO> productQuantityDTOs = new ArrayList<>();
        for (CartItem item : cartItems) {
            ProductQuantityDTO productQuantityDTO = new ProductQuantityDTO();
            productQuantityDTO.setProduct(item);
            productQuantityDTO.setQuantity(item.getQuantity());
            productQuantityDTOs.add(productQuantityDTO);
        }
        return productQuantityDTOs;

    }

       // In CartService.java
   public Long getCartIdByUserId(Long userId) {
       logger.info("Fetching cart ID for User ID: {}", userId);
       Cart cart = cartRepository.findByUserId(userId)
               .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));
       return cart.getId();
   }
   public void checkoutCart(Long cartId) {
    logger.info("Cart Service: Checking out cart ID: {}", cartId);

}
//*****************************
}

