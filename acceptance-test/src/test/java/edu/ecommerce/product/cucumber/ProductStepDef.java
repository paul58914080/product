package edu.ecommerce.product.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import edu.ecommerce.product.domain.model.Product;
import edu.ecommerce.product.repository.dao.ProductDao;
import edu.ecommerce.product.repository.entity.ProductEntity;
import edu.ecommerce.product.rest.generated.model.ProductInfo;
import edu.ecommerce.product.rest.generated.model.ProblemDetail;

public class ProductStepDef implements En {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/products";
  @LocalServerPort private int port;
  private ResponseEntity responseEntity;

  public ProductStepDef(TestRestTemplate restTemplate, ProductDao productDao) {

    DataTableType(
        (Map<String, String> row) ->
            Product.builder()
                .code(Long.parseLong(row.get("code")))
                .description(row.get("description"))
                .build());
    DataTableType(
        (Map<String, String> row) ->
            ProductEntity.builder()
                .code(Long.parseLong(row.get("code")))
                .description(row.get("description"))
                .build());

    Before((HookNoArgsBody) productDao::deleteAll);
    After((HookNoArgsBody) productDao::deleteAll);

    Given(
        "the following products exists in the library",
        (DataTable dataTable) -> {
          List<ProductEntity> poems = dataTable.asList(ProductEntity.class);
          productDao.saveAll(poems);
        });

    When(
        "user requests for all products",
        () -> {
          String url = LOCALHOST + port + API_URI;
          responseEntity = restTemplate.getForEntity(url, ProductInfo.class);
        });

    When(
        "user requests for products by code {string}",
        (String code) -> {
          String url = LOCALHOST + port + API_URI + "/" + code;
          responseEntity = restTemplate.getForEntity(url, Product.class);
        });

    When(
        "user requests for products by id {string} that does not exists",
        (String code) -> {
          String url = LOCALHOST + port + API_URI + "/" + code;
          responseEntity = restTemplate.getForEntity(url, ProblemDetail.class);
        });

    Then(
        "the user gets an exception {string}",
        (String exception) -> {
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
          var actualResponse = (ProblemDetail) responseEntity.getBody();
          var expectedProblemDetail =
              ProblemDetail.builder()
                  .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
                  .status(HttpStatus.NOT_FOUND.value())
                  .detail("Product with code 10000 does not exist")
                  .instance("/api/v1/products/10000")
                  .title("Product not found")
                  .build();
          assertThat(actualResponse).isNotNull();
          assertThat(actualResponse)
              .usingRecursiveComparison()
              .ignoringFields("timestamp")
              .isEqualTo(expectedProblemDetail);
          assertThat(actualResponse.getTimestamp())
              .isCloseTo(LocalDateTime.now(), within(100L, ChronoUnit.SECONDS));
        });

    Then(
        "the user gets the following products",
        (DataTable dataTable) -> {
          List<Product> expectedProducts = dataTable.asList(Product.class);
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
          Object body = responseEntity.getBody();
          assertThat(body).isNotNull();
          if (body instanceof ProductInfo) {
            assertThat(((ProductInfo) body).getProducts())
                .isNotEmpty()
                .extracting("description")
                .containsAll(
                    expectedProducts.stream()
                        .map(Product::getDescription)
                        .collect(Collectors.toList()));
          } else if (body instanceof Product) {
            assertThat(body)
                .isNotNull()
                .extracting("description")
                .isEqualTo(expectedProducts.get(0).getDescription());
          }
        });
  }
}
