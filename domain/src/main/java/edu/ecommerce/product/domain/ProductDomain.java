package edu.ecommerce.product.domain;

import java.util.List;
import lombok.NonNull;
import edu.ecommerce.product.domain.exception.ProductNotFoundException;
import edu.ecommerce.product.domain.model.Product;
import edu.ecommerce.product.domain.port.ObtainProduct;
import edu.ecommerce.product.domain.port.RequestProduct;

public class ProductDomain implements RequestProduct {

  private final ObtainProduct obtainProduct;

  public ProductDomain() {
    this(new ObtainProduct() {});
  }

  public ProductDomain(ObtainProduct obtainProduct) {
    this.obtainProduct = obtainProduct;
  }

  @Override
  public List<Product> getProducts() {
    return obtainProduct.getAllProducts();
  }

  @Override
  public Product getProductByCode(@NonNull Long code) {
    var product = obtainProduct.getProductByCode(code);
    return product.orElseThrow(() -> new ProductNotFoundException(code));
  }
}
