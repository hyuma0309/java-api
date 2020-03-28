package com.teamlab.engineering.restfulapi.exception;

/**
 * 画像が削除できなかった場合の例外クラス
 *
 * @author asada
 */
public class ProductImageNotDeletedException extends RuntimeException {

  private static final long serialVersionUID = -7626616366228158111L;

  public ProductImageNotDeletedException(String message) {
    super(message);
  }
}
