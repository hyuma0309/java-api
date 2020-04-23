package com.teamlab.engineering.restfulapi.exception;

/**
 * Filterのエラーレスポンス
 *
 * @author asada
 */
public class ErrorResponse {

  private String message;
  private String detail;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  /**
   * エラーメッセージ
   *
   * @param message エラー概要
   * @param detail エラー詳細
   */
  public ErrorResponse(String message, String detail) {
    this.message = message;
    this.detail = detail;
  }
}
