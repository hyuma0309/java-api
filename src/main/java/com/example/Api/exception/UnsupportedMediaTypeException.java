package com.example.Api.exception;

/**
 * 画像ファイルの拡張子の不正に対するExceptionクラス
 *
 * @author asada
 */
public class UnsupportedMediaTypeException extends RuntimeException {
  private static final long serialVersionUID = 8394000014819218455L;

  public UnsupportedMediaTypeException(String message) {
    super(message);
  }
}
