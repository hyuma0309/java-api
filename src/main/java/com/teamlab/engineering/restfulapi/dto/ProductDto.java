package com.teamlab.engineering.restfulapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamlab.engineering.restfulapi.entitiy.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品DTO
 *
 * @author asada
 */
@Data
@AllArgsConstructor
public class ProductDto {
  private long id;
  private String title;
  private String description;
  private Integer price;
  private String imagePath;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  public ProductDto(Product product) {
    id = product.getId();
    title = product.getTitle();
    description = product.getDescription();
    price = product.getPrice();
    imagePath = product.getImagePath();
    createTime = product.getCreateTime();
    updateTime = product.getUpdateTime();
  }
}
