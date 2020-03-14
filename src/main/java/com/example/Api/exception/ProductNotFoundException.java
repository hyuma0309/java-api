package com.example.Api.exception;

/**
 * 存在しないURLにアクセスした時の例外クラス
 *
 * @author asada
 */
public class ProductNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 5179398220343773826L;

  public ProductNotFoundException(Long id) {
    super("idが" + id + "の商品は見つかりませんでした。");
  }
}
