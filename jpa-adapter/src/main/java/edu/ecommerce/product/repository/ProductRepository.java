package edu.ecommerce.product.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import edu.ecommerce.product.domain.model.Product;
import edu.ecommerce.product.domain.port.ObtainProduct;
import edu.ecommerce.product.repository.dao.ProductDao;
import edu.ecommerce.product.repository.entity.ProductEntity;

public class ProductRepository implements ObtainProduct {

  private final ProductDao productDao;

  public ProductRepository(ProductDao productDao) {
    this.productDao = productDao;
  }

  @Override
  public List<Product> getAllProducts() {
    return productDao.findAll().stream().map(ProductEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<Product> getProductByCode(Long code) {
    var productEntity = productDao.findByCode(code);
    return productEntity.map(ProductEntity::toModel);
  }
}
