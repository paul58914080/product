package edu.ecommerce.product.repository;

import edu.ecommerce.product.repository.config.JpaAdapterConfig;
import net.lbruun.springboot.preliquibase.PreLiquibaseAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class ProductJpaAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductJpaAdapterApplication.class, args);
  }

  @TestConfiguration
  @Import(JpaAdapterConfig.class)
  @ImportAutoConfiguration({PreLiquibaseAutoConfiguration.class})
  static class ProductJpaTestConfig {}
}
