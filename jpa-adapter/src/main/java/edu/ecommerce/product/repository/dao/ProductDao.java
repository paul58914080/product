package edu.ecommerce.product.repository.dao;

import edu.ecommerce.product.repository.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao
    extends JpaRepository<ProductEntity, Long>, RevisionRepository<ProductEntity, Long, Long> {

  Optional<ProductEntity> findByCode(Long code);
}
