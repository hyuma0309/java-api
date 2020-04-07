package com.teamlab.engineering.restfulapi.exception;

/**
 * 存在しない商品IDが指定された場合に発生する例外クラス
 *
 * @author asada
 */
public class ProductNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 5179398220343773826L;

  public ProductNotFoundException(Long id) {
    super("idが" + id + "の商品は見つかりませんでした。");
  }
}
