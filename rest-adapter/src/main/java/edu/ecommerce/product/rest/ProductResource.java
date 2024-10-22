package edu.ecommerce.product.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import edu.ecommerce.product.domain.model.Product;
import edu.ecommerce.product.domain.port.RequestProduct;
import edu.ecommerce.product.rest.generated.api.ProductApi;
import edu.ecommerce.product.rest.generated.model.ProductInfo;

@RestController
public class ProductResource implements ProductApi {

  private final RequestProduct requestProduct;

  public ProductResource(RequestProduct requestProduct) {
    this.requestProduct = requestProduct;
  }

  public ResponseEntity<ProductInfo> getProducts() {
    return ResponseEntity.ok(ProductInfo.builder().products(requestProduct.getProducts()).build());
  }

  public ResponseEntity<Product> getProductByCode(@PathVariable("code") Long code) {
    return ResponseEntity.ok(requestProduct.getProductByCode(code));
  }
}
