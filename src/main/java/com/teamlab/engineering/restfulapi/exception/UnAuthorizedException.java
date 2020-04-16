package com.teamlab.engineering.restfulapi.exception;

/**
 * ログイン認証ができなかった場合に対するException
 *
 * @author asada
 */
public class UnAuthorizedException extends RuntimeException {
  private static final long serialVersionUID = 8494714978563916944L;

  public UnAuthorizedException(String message) {
    super(message);
  }
}
