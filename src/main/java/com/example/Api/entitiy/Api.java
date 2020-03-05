package com.example.Api.entitiy;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "items")
@Data
public class Api {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(min = 1, max = 30)
  private String name;

  @NotNull
  @Size(min = 1, max = 30)
  private String description;

  @NotNull
  @Min(value = 0, message = "正しい価格を入力してください")
  private Integer price;

  private String image;
}
