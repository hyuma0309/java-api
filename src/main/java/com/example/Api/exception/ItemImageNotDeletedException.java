package com.example.Api.exception;

public class ItemImageNotDeletedException extends RuntimeException {

    private static final long serialVersionUID = -7626616366228158111L;

    public ItemImageNotDeletedException(String message) {
    super(message);
  }
}
