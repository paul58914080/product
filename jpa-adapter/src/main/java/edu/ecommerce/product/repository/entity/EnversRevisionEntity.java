package edu.ecommerce.product.repository.entity;

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

@Table(name = "REVISION_INFO", schema = "PRODUCT_AUDIT")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
    schema = "PRODUCT_AUDIT",
    name = "SEQ_REVISION_INFO",
    sequenceName = "PRODUCT_AUDIT.SEQ_REVISION_INFO",
    allocationSize = 1)
public class EnversRevisionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVISION_INFO")
  @Column(name = "REV")
  private Long rev;

  @Column(name = "TIMESTAMP")
  private Long code;
}
