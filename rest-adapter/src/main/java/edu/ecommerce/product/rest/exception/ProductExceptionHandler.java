package edu.ecommerce.product.rest.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import edu.ecommerce.product.domain.exception.ProductNotFoundException;
import edu.ecommerce.product.rest.generated.model.ProblemDetail;

@RestControllerAdvice(basePackages = {"edu.ecommerce.product"})
public class ProductExceptionHandler {

  @ExceptionHandler(value = ProductNotFoundException.class)
  public final ResponseEntity<ProblemDetail> handleProductNotFoundException(
      final Exception exception, final WebRequest request) {
    var problem =
        ProblemDetail.builder()
            .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
            .status(HttpStatus.NOT_FOUND.value())
            .title("Product not found")
            .detail(exception.getMessage())
            .instance(((ServletWebRequest) request).getRequest().getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
  }
}
