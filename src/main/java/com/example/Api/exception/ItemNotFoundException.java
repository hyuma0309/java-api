package com.example.Api.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("idが" + id + "の商品は見つかりませんでした。");
    }
}
