package com.teamlab.engineering.restfulapi.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 商品フォーム
 *
 * @author asada
 */
public class ProductForm {
  @NotBlank(message = "{error.validation.title-not-blank}")
  @Size(max = 100, message = "{validation.title-too-long}")
  private String title;

  @NotBlank(message = "{error.validation.description-not-blank}")
  @Size(max = 500, message = "{validation.description-too-long}")
  private String description;

  @NotNull(message = "{error.validation.price-not-null}")
  @Min(value = 1, message = "{error.validation.price-too-low}")
  @Max(value = 1000000, message = "{error.validation.price-too-height}")
  private Integer price;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
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
}
