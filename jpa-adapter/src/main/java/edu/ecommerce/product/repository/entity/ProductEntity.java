package edu.ecommerce.product.repository.entity;

import edu.ecommerce.product.domain.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Table(name = "T_PRODUCT")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_PRODUCT")
  @SequenceGenerator(
      name = "SEQ_T_PRODUCT",
      sequenceName = "SEQ_T_PRODUCT",
      allocationSize = 1,
      initialValue = 1)
  @Column(name = "TECH_ID")
  private Long techId;

  @Column(name = "CODE")
  private Long code;

  @Column(name = "DESCRIPTION")
  private String description;

  public Product toModel() {
    return Product.builder().code(code).description(description).build();
  }
}
