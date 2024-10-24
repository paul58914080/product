package edu.ecommerce.product.boot.config;

import edu.ecommerce.product.domain.ProductDomain;
import edu.ecommerce.product.domain.port.ObtainProduct;
import edu.ecommerce.product.domain.port.RequestProduct;
import edu.ecommerce.product.repository.config.JpaAdapterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestProduct getRequestProduct(ObtainProduct obtainProduct) {
    return new ProductDomain(obtainProduct);
  }
}
