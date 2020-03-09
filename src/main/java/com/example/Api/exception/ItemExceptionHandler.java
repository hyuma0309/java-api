package com.example.Api.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.jws.WebResult;

/**
 * 商品情報APIの例外
 *
 * @author asada
 */
@RestControllerAdvice
public class ItemExceptionHandler extends ResponseEntityExceptionHandler {

  /** ID等のリソースが無い場合に呼び出し */
  @ExceptionHandler(ItemNotFoundException.class)
  public ResponseEntity<Object> handleItemNotFoundException(
      ItemNotFoundException ex, WebResult request) {
    return super.handleExceptionInternal(
        ex, "handleItemNotFoundException", null, HttpStatus.NOT_FOUND, (WebRequest) request);
  }

  /** 画像の削除に失敗した時の呼び出し */
  @ExceptionHandler(ItemImageNotDeletedException.class)
  public ResponseEntity<Object> handleItemImageNotDeletedException(
      ItemImageNotDeletedException e, WebRequest request) {
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
    return handleExceptionInternal(ex, "正しい値を入力してください", headers, status, request);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, "無効なURLのため表示できません", headers, status, request);
  }

  /** どこにもキャッチされなかったら呼ばれる */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
    return super.handleExceptionInternal(
        ex, "サーバーエラーが発生しました。", null, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
