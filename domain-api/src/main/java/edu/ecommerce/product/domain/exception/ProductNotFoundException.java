package edu.ecommerce.product.domain.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long id) {
    super("Product with code " + id + " does not exist");
  }
}
