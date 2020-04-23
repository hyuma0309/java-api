package com.teamlab.engineering.restfulapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

/**
 * 商品情報APIの例外
 *
 * @author asada
 */
@RestControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  public ProductExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  private static final Logger logger = LoggerFactory.getLogger(ProductExceptionHandler.class);

  /** ID等のリソースが無い場合に呼び出し */
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<Object> handleProductNotFoundException(
      ProductNotFoundException e, WebRequest request) {
    logger.warn(e.getMessage(), e);
    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.ProductNotFound", null, Locale.JAPAN));
    return super.handleExceptionInternal(e, errorResponse, null, HttpStatus.NOT_FOUND, request);
  }

  /** 画像の削除に失敗した時の呼び出し */
  @ExceptionHandler(ProductImageNotDeletedException.class)
  public ResponseEntity<Object> handleProductImageNotDeletedException(
      ProductImageNotDeletedException e, WebRequest request) {
    logger.warn(e.getMessage(), e);
    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.ImageNotDeleted", null, Locale.JAPAN));
    return super.handleExceptionInternal(e, errorResponse, null, HttpStatus.BAD_REQUEST, request);
  }

  /** 未設定の拡張子を使用した場合の呼び出し */
  @ExceptionHandler(UnsupportedMediaTypeException.class)
  public ResponseEntity<Object> handleUnsupportedMediaTypeException(
      UnsupportedMediaTypeException e, WebRequest request) {
    logger.warn(e.getMessage(), e);

    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.UnsupportedMediaType", null, Locale.JAPAN));
    return super.handleExceptionInternal(
        e, errorResponse, null, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
  }

  /** 存在している商品タイトル登録時呼び出し */
  @ExceptionHandler(AlreadyExistTitleException.class)
  public ResponseEntity<Object> handleAlreadyExistTitleException(
      AlreadyExistTitleException e, WebRequest request) {
    logger.warn(e.getMessage(), e);

    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.AlreadyExistTitle", null, Locale.JAPAN));
    return super.handleExceptionInternal(e, errorResponse, null, HttpStatus.BAD_REQUEST, request);
  }
  /** 画像が存在しない場合の呼び出し　 */
  @ExceptionHandler(ProductNotImageException.class)
  public ResponseEntity<Object> handleProductNotImageException(
      ProductNotImageException e, WebRequest request) {
    logger.warn(e.getMessage(), e);

    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.NotImage", null, Locale.JAPAN));

    return super.handleExceptionInternal(e, errorResponse, null, HttpStatus.NOT_FOUND, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    logger.warn(e.getMessage(), e);

    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.MethodArgumentNotValid", null, Locale.JAPAN));

    return super.handleExceptionInternal(e, errorResponse, null, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
    logger.warn(e.getMessage(), e);

    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.TypeMismatch", null, Locale.JAPAN));

    return super.handleExceptionInternal(e, errorResponse, null, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
    logger.warn(e.getMessage(), e);
    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.NoHandlerFound", null, Locale.JAPAN));

    return super.handleExceptionInternal(e, errorResponse, null, status, request);
  }

  /** どこにもキャッチされなかったら呼ばれる */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllException(
      Exception e, HttpHeaders headers, HttpStatus status, WebRequest request) {
    logger.error(e.getMessage(), e);

    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage("error.exception.handleAll", null, Locale.JAPAN));

    return super.handleExceptionInternal(e, errorResponse, null, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException e,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    logger.warn(e.getMessage(), e);
    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.exception.failed", null, Locale.JAPAN),
            messageSource.getMessage(
                "error.exception.MissingServletRequestParameter", null, Locale.JAPAN));

    return super.handleExceptionInternal(e, errorResponse, null, status, request);
  }
}
