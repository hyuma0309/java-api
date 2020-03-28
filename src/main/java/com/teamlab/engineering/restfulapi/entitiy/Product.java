package com.teamlab.engineering.restfulapi.entitiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 商品Entity
 *
 * @author asada
 */
@Entity
@Table(name = "products")
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank(message = "{validation.title-not-blank}")
  @Size(max = 100, message = "{validation.title-too-long}")
  private String title;

  @NotBlank(message = "{validation.description-not-blank}")
  @Size(max = 500, message = "{validation.description-too-long}")
  private String description;

  @NotNull(message = "{validation.price-not-null}")
  @Min(value = 1, message = "{validation.price-too-low}")
  @Max(value = 1000000, message = "{validation.description-too-height}")
  private Integer price;

  private String imagePath;

  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  @UpdateTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
}
