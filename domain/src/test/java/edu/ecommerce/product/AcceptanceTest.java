package edu.ecommerce.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.ecommerce.product.domain.ProductDomain;
import edu.ecommerce.product.domain.exception.ProductNotFoundException;
import edu.ecommerce.product.domain.model.Product;
import edu.ecommerce.product.domain.port.ObtainProduct;

@ExtendWith(MockitoExtension.class)
public class AcceptanceTest {

  @Test
  @DisplayName("should be able to get products when asked for products from hard coded products")
  public void getProductsFromHardCoded() {
    /*
       RequestProduct    - left side port
       ProductDomain     - hexagon (domain)
       ObtainProduct     - right side port
    */
    var requestProduct = new ProductDomain(); // the product is hard coded
    var products = requestProduct.getProducts();
    assertThat(products)
        .hasSize(1)
        .extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get products when asked for products from stub")
  public void getProductsFromMockedStub(@Mock ObtainProduct obtainProduct) {
    // Stub
    var product =
        Product.builder()
            .code(1L)
            .description(
                "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
            .build();
    Mockito.lenient().when(obtainProduct.getAllProducts()).thenReturn(List.of(product));
    // hexagon
    var requestProduct = new ProductDomain(obtainProduct);
    var products = requestProduct.getProducts();
    assertThat(products)
        .hasSize(1)
        .extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get product when asked for product by id from stub")
  public void getProductByIdFromMockedStub(@Mock ObtainProduct obtainProduct) {
    // Given
    // Stub
    var code = 1L;
    var description =
        "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    var expectedProduct = Product.builder().code(code).description(description).build();
    Mockito.lenient()
        .when(obtainProduct.getProductByCode(code))
        .thenReturn(Optional.of(expectedProduct));
    // When
    var requestProduct = new ProductDomain(obtainProduct);
    var actualProduct = requestProduct.getProductByCode(code);
    assertThat(actualProduct).isNotNull().isEqualTo(expectedProduct);
  }

  @Test
  @DisplayName("should throw exception when asked for product by id that does not exists from stub")
  public void getExceptionWhenAskedProductByIdThatDoesNotExist(@Mock ObtainProduct obtainProduct) {
    // Given
    // Stub
    var code = -1000L;
    Mockito.lenient().when(obtainProduct.getProductByCode(code)).thenReturn(Optional.empty());
    // When
    var requestProduct = new ProductDomain(obtainProduct);
    // Then
    assertThatThrownBy(() -> requestProduct.getProductByCode(code))
        .isInstanceOf(ProductNotFoundException.class)
        .hasMessageContaining("Product with code " + code + " does not exist");
  }
}
