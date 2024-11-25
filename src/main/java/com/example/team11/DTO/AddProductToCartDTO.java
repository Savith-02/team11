package com.example.team11.DTO;

public class AddProductToCartDTO {
    private Long productId;
    private int quantity;
    public void setProductId(Long productId) {
      this.productId = productId;
    }
    public Long getProductId() {
      return productId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
