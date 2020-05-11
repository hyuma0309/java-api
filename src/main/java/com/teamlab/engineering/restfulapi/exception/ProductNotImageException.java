package com.teamlab.engineering.restfulapi.exception;

/**
 * 画像ファイルが存在しない場合に対するExceptionクラス
 *
 * @author asada
 */
public class ProductNotImageException extends RuntimeException {
  private static final long serialVersionUID = 1159085182833438487L;

  public ProductNotImageException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductNotImageException(String message) {
    super(message);
  }
}
