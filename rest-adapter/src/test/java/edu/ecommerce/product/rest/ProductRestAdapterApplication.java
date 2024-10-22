package edu.ecommerce.product.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import edu.ecommerce.product.domain.port.RequestProduct;

@SpringBootApplication
@ComponentScan(basePackages = "edu.ecommerce.product")
public class ProductRestAdapterApplication {

  @MockBean private RequestProduct requestProduct;

  public static void main(String[] args) {
    SpringApplication.run(ProductRestAdapterApplication.class, args);
  }
}
