package edu.ecommerce.product.domain.port;

import java.util.List;
import lombok.NonNull;
import edu.ecommerce.product.domain.model.Product;

public interface RequestProduct {

  List<Product> getProducts();

  Product getProductByCode(@NonNull Long code);
}
