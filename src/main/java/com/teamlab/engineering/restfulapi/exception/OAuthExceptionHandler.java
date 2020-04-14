package com.teamlab.engineering.restfulapi.exception;

import com.teamlab.engineering.restfulapi.controller.OAuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * OAuth用のExceptionHandlerクラス
 *
 * @author asada
 */
@ControllerAdvice(assignableTypes = OAuthController.class)
public class OAuthExceptionHandler {
  /**
   * ログイン認証ができていない場合の例外処理
   *
   * @param ex UnAuthorizedException
   * @return error/401.html
   */
  private static final Logger logger = LoggerFactory.getLogger(ProductExceptionHandler.class);

  @ExceptionHandler(UnAuthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public String handleUnAuthorizedException(UnAuthorizedException ex) {
    logger.warn(ex.getMessage(), ex);
    return "error/401";
  }

  /**
   * その他の例外処理
   *
   * @param ex Exception
   * @return error/500.html
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleInternalServer(Exception ex) {
    logger.warn(ex.getMessage(), ex);
    return "error/500";
  }
}
