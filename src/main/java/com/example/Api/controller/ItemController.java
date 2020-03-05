package com.example.api.controller;

import com.example.Api.entitiy.Api;
import com.example.Api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/items")
public class ItemController {
  private final ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  /** 商品情報の一覧 */
  @GetMapping("")
  // HTTPステータスとして、”200 OK”を設定するため、value属性にはHttpStatus.OKを設定する。
  @ResponseStatus(HttpStatus.OK)
  public List<Api> index() {
    return itemService.findAll();
  }

  /** 商品情報の登録 */
  @PostMapping("")
  // HTTPステータスとして、”201 Created”を設定するため、value属性にはHttpStatus.CREATEDを設定する。
  @ResponseStatus(HttpStatus.CREATED)
  public Api create(@RequestBody @Validated Api item) {
    return itemService.create(item);
  }

  /** 商品情報1件の取得 */
  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public Api show(@PathVariable Long id) {
    return itemService.findItem(id);
  }

  /** 商品情報の更新 */
  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public Api update(@PathVariable Long id, @RequestBody @Validated Api item) {
    return itemService.update(id, item);
  }

  /** 商品情報の削除 */
  @DeleteMapping("{id}")
  // 204
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    itemService.delete(id);
  }

  /** 画像情報の取得 */
  @GetMapping("{id}/image")
  @ResponseStatus(HttpStatus.OK)
  public HttpEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
    return itemService.getImage(id);
  }

  /** 画像情報の更新 */
  @PutMapping("{id}/image")
  @ResponseStatus(HttpStatus.OK)
  public Api uploadImage(@PathVariable Long id, @RequestParam(name = "image") MultipartFile file) {
    return itemService.uploadImage(id, file);
  }

  /** 画像情報の削除 */
  @DeleteMapping("{id}/image")
  @ResponseStatus(HttpStatus.OK)
  public Api deleteImage(@PathVariable Long id) {
    return itemService.deleteImage(id);
  }

  /** 商品情報の検索 */
  @GetMapping("search")
  @ResponseStatus(HttpStatus.OK)
  public List<Api> search(@RequestParam String keyword) {
    return itemService.search(keyword);
  }
}
