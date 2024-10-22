package edu.ecommerce.product.domain.port;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import edu.ecommerce.product.domain.model.Product;

public interface ObtainProduct {

  default List<Product> getAllProducts() {
    Product product =
        Product.builder()
            .code(1L)
            .description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
            .build();
    return List.of(product);
  }

  default Optional<Product> getProductByCode(@NonNull Long code) {
    return Optional.of(
        Product.builder()
            .code(1L)
            .description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
            .build());
  }
}
