package com.teamlab.engineering.restfulapi.exception;

/**
 * AlreadyExistTitleExceptionのエラークラス
 *
 * @author asada
 */
public class AlreadyExistTitleException extends RuntimeException {
  private static final long serialVersionUID = 5991584172897204222L;

  /** @param message メッセージ */
  public AlreadyExistTitleException(String message) {
    super(message);
  }
}
