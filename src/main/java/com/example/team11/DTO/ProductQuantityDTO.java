package com.example.team11.DTO;

import com.example.team11.Entity.CartItem;

public class ProductQuantityDTO {
    private CartItem product;
    private int quantity;

    // Getters and Setters
    public CartItem getProduct() {
        return product;
    }

    public void setProduct(CartItem product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}