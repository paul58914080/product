package edu.ecommerce.product.domain.port;

import edu.ecommerce.product.domain.model.Product;
import java.util.List;
import lombok.NonNull;

public interface RequestProduct {

  List<Product> getProducts();

  Product getProductByCode(@NonNull Long code);
}
