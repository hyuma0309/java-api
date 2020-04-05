package com.teamlab.engineering.restfulapi.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductForm {
  @NotBlank(message = "{validation.title-not-blank}")
  @Size(max = 100, message = "{validation.title-too-long}")
  private String title;

  @NotBlank(message = "{validation.description-not-blank}")
  @Size(max = 500, message = "{validation.description-too-long}")
  private String description;

  @NotNull(message = "{validation.price-not-null}")
  @Min(value = 1, message = "{validation.price-too-low}")
  @Max(value = 1000000, message = "{validation.price-too-height}")
  private Integer price;
}
