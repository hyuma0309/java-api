package com.teamlab.engineering.restfulapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamlab.engineering.restfulapi.entitiy.Product;

import java.time.LocalDateTime;

/**
 * 商品DTO
 *
 * @author asada
 */
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

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
