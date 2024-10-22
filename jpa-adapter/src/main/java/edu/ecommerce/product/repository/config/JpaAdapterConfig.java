package edu.ecommerce.product.repository.config;

import edu.ecommerce.product.domain.port.ObtainProduct;
import edu.ecommerce.product.repository.ProductRepository;
import edu.ecommerce.product.repository.dao.ProductDao;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("edu.ecommerce.product.repository.entity")
@EnableJpaRepositories(
    basePackages = "edu.ecommerce.product.repository.dao",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class JpaAdapterConfig {

  @Bean
  public ObtainProduct getProductRepository(ProductDao productDao) {
    return new ProductRepository(productDao);
  }
}
