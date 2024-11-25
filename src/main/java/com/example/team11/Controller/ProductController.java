package com.example.team11.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.team11.DTO.ProductDTO;
import com.example.team11.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        logger.info("Product Controller: Inside getAllProducts");
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        logger.info("Product Controller: Inside getProductById, Product ID: {}", id);
        ProductDTO product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product/create")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        logger.info("Product Controller: Inside addProduct, Product: {}", productDTO);
        productService.addProduct(productDTO);
        return ResponseEntity.ok("Product added successfully!");
    }

    @PutMapping("/product/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        logger.info("Product Controller: Inside updateProduct, Product: {}", productDTO);
        boolean updated = productService.updateProduct(productDTO);
        if (updated) {
            return ResponseEntity.ok("Product updated successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        logger.info("Product Controller: Inside deleteProduct, Product ID: {}", id);
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products/categories")
    public List<String> getCategorys() {
        logger.info("Product Controller: Inside getCategorys");
        return productService.getCategorys();
    }
}
