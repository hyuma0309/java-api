package com.teamlab.engineering.restfulapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
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

/**
 * 商品情報APIの例外
 *
 * @author asada
 */
@Slf4j
@RestControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

  /** ID等のリソースが無い場合に呼び出し */
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<Object> handleItemNotFoundException(
      ProductNotFoundException ex, WebRequest request) {
    return super.handleExceptionInternal(ex, "商品情報が見つかりません", null, HttpStatus.NOT_FOUND, request);
  }

  /** 画像の削除に失敗した時の呼び出し */
  @ExceptionHandler(ProductImageNotDeletedException.class)
  public ResponseEntity<Object> handleItemImageNotDeletedException(
      ProductImageNotDeletedException e, WebRequest request) {
    return super.handleExceptionInternal(
        e, "商品画像の削除に失敗しました", null, HttpStatus.BAD_REQUEST, request);
  }

  /** 未設定の拡張子を使用した場合の呼び出し */
  @ExceptionHandler(UnsupportedMediaTypeException.class)
  public ResponseEntity<Object> handleUnsupportedMediaTypeException(
      UnsupportedMediaTypeException e, WebRequest request) {
    return super.handleExceptionInternal(
        e, "商品画像の削除に失敗しました", null, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
  }

  /** 存在している商品タイトル登録時呼び出し */
  @ExceptionHandler(AlreadyExistTitleException.class)
  public ResponseEntity<Object> handleAlreadyExistTitleException(
      AlreadyExistTitleException e, WebRequest request) {
    return super.handleExceptionInternal(e, "商品の登録に失敗しました", null, HttpStatus.BAD_REQUEST, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    log.warn(ex.getMessage(), ex);
    return super.handleExceptionInternal(ex, "正しい値を入力してください", headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleExceptionInternal(ex, "パラメーターが不正な書式です", headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.warn(ex.getMessage(), ex);
    return super.handleExceptionInternal(ex, "存在しないURLです", headers, status, request);
  }

  /** どこにもキャッチされなかったら呼ばれる */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
    return super.handleExceptionInternal(
        ex, "サーバーエラーが発生しました。", null, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleExceptionInternal(ex, "パラメータが不正です", headers, status, request);
  }
}
