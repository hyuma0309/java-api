package com.example.Api.exception;

/**
 * 画像が削除できなかった場合の例外クラス
 *
 * @author asada
 */
public class ItemImageNotDeletedException extends RuntimeException {

  private static final long serialVersionUID = -7626616366228158111L;

  public ItemImageNotDeletedException(String message) {
    super(message);
  }
}
