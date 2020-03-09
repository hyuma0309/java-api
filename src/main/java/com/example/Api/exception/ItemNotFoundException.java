package com.example.Api.exception;

/**
 * 存在しないURLにアクセスした時の例外クラス
 *
 * @author asada
 */
public class ItemNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5179398220343773826L;

    public ItemNotFoundException(Long id) {
    super("idが" + id + "の商品は見つかりませんでした。");
  }
}
