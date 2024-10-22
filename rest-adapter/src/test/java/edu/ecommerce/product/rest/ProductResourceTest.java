package edu.ecommerce.product.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import edu.ecommerce.product.domain.exception.ProductNotFoundException;
import edu.ecommerce.product.domain.model.Product;
import edu.ecommerce.product.domain.port.RequestProduct;
import edu.ecommerce.product.rest.generated.model.ProblemDetail;
import edu.ecommerce.product.rest.generated.model.ProductInfo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ProductRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ProductResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/products";
  @LocalServerPort private int port;
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private RequestProduct requestProduct;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give products when asked for products with the support of domain stub")
  public void obtainProductsFromDomainStub() {
    // Given
    var product = Product.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestProduct.getProducts()).thenReturn(List.of(product));
    // When
    var url = LOCALHOST + port + API_URI;
    var responseEntity = restTemplate.getForEntity(url, ProductInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getProducts())
        .isNotEmpty()
        .extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName(
      "should give the product when asked for an product by code with the support of domain stub")
  public void obtainProductByCodeFromDomainStub() {
    // Given
    var code = 1L;
    var description = "Johnny Johnny Yes Papa !!";
    var product = Product.builder().code(code).description(description).build();
    Mockito.lenient().when(requestProduct.getProductByCode(code)).thenReturn(product);
    // When
    var url = LOCALHOST + port + API_URI + "/" + code;
    var responseEntity = restTemplate.getForEntity(url, Product.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(product);
  }

  @Test
  @DisplayName(
      "should give exception when asked for an product by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnProductByCodeFromDomainStub() {
    // Given
    var code = -1000L;
    Mockito.lenient()
        .when(requestProduct.getProductByCode(code))
        .thenThrow(new ProductNotFoundException(code));
    // When
    var url = LOCALHOST + port + API_URI + "/" + code;
    var responseEntity = restTemplate.getForEntity(url, ProblemDetail.class);
    // Then
    var expectedProblemDetail =
        ProblemDetail.builder()
            .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
            .status(HttpStatus.NOT_FOUND.value())
            .detail("Product with code -1000 does not exist")
            .instance("/api/v1/products/-1000")
            .title("Product not found")
            .build();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody())
        .usingRecursiveComparison()
        .ignoringFields("timestamp")
        .isEqualTo(expectedProblemDetail);
    assertThat(responseEntity.getBody().getTimestamp())
        .isCloseTo(LocalDateTime.now(), within(100L, ChronoUnit.SECONDS));
  }
}
