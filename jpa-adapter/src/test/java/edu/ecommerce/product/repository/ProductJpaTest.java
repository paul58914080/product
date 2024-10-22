package edu.ecommerce.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ecommerce.product.domain.model.Product;
import edu.ecommerce.product.domain.port.ObtainProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ProductJpaTest {

  @Autowired private ObtainProduct obtainProduct;

  @Test
  @DisplayName("should start the application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName(
      "given products exist in database when asked should return all products from database")
  public void shouldGiveMeProductsWhenAskedGivenProductExistsInDatabase() {
    // Given from @Sql
    // When
    var products = obtainProduct.getAllProducts();
    // Then
    assertThat(products)
        .isNotNull()
        .extracting("description")
        .contains("Twinkle twinkle little star");
  }

  @Test
  @DisplayName("given no products exists in database when asked should return empty")
  public void shouldGiveNoProductWhenAskedGivenProductsDoNotExistInDatabase() {
    // When
    var products = obtainProduct.getAllProducts();
    // Then
    assertThat(products).isNotNull().isEmpty();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName(
      "given products exists in database when asked for product by id should return the product")
  public void shouldGiveTheProductWhenAskedByIdGivenThatProductByThatIdExistsInDatabase() {
    // Given from @Sql
    // When
    var product = obtainProduct.getProductByCode(1L);
    // Then
    assertThat(product)
        .isNotNull()
        .isNotEmpty()
        .get()
        .isEqualTo(Product.builder().code(1L).description("Twinkle twinkle little star").build());
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName(
      "given products exists in database when asked for product by id that does not exist should give empty")
  public void shouldGiveNoProductWhenAskedByIdGivenThatProductByThatIdDoesNotExistInDatabase() {
    // Given from @Sql
    // When
    var product = obtainProduct.getProductByCode(-1000L);
    // Then
    assertThat(product).isEmpty();
  }
}
