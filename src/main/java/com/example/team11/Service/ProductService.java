package com.example.team11.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.team11.DTO.ProductDTO;
import com.example.team11.Entity.Product;
import com.example.team11.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<ProductDTO> getAllProducts() {
        logger.info("Service: Inside getAllProducts");
        return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    public ProductDTO getProductById(long id) {
        logger.info("Service: Inside getProductById, Product ID: {}", id);
        return productRepository.findById((long) id).map(this::convertToDTO).orElse(null);
    }

    public void addProduct(ProductDTO productDTO) {
        logger.info("Service: Inside addProduct, Product: {}", productDTO);
        Product product = convertToEntity(productDTO);
        productRepository.save(product);
    }

    public boolean updateProduct(ProductDTO productDTO) {
        logger.info("Service: Inside updateProduct, Product: {}", productDTO);
        if (productRepository.existsById((long) productDTO.getId())) {
            Product product = convertToEntity(productDTO);
            productRepository.save(product);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        logger.info("Service: Inside deleteProduct, Product ID: {}", id);
        if (productRepository.existsById((long) id)) {
            productRepository.deleteById((long) id);
            return true;
        }
        return false;
    }

    // Conversion methods
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setCategory(product.getCategory());
        productDTO.setSupplierId(product.getSupplierId());
        return productDTO;
    }

    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(productDTO.getCategory());
        product.setSupplierId(productDTO.getSupplierId());
        return product;
    }

    public List<String> getCategorys() {
        logger.info("Service: Inside getCategorys");
        return productRepository.findAll().stream().map(Product::getCategory).distinct().collect(Collectors.toList());
    }
    
}
