package com.teamlab.engineering.restfulapi.exception;

public class ProductNotImageException extends RuntimeException {
  private static final long serialVersionUID = 1159085182833438487L;

  public ProductNotImageException(String message) {
    super(message);
  }
}
