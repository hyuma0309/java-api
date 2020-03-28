package com.teamlab.engineering.restfulapi.controller;

import com.teamlab.engineering.restfulapi.entitiy.Product;
import com.teamlab.engineering.restfulapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

/**
 * 商品コントローラー
 *
 * @author asada
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {
  private final ProductService itemService;

  /**
   * 商品検索
   *
   * @param title 検索キーワード
   * @return 検索結果
   */
  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public List<Product> search(@RequestParam String title) {
    return itemService.search(title);
  }

  /**
   * 商品登録
   *
   * @param item 商品情報1件
   * @return item
   */
  @PostMapping("")
  // HTTPステータスとして、”201 Created”を設定するため、value属性にはHttpStatus.CREATEDを設定する。
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@RequestBody @Validated Product item) {
    return itemService.create(item);
  }

  /**
   * 商品1件取得
   *
   * @param id 商品ID
   * @return 商品情報1件
   */
  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product show(@PathVariable Long id) {
    return itemService.findItem(id);
  }

  /**
   * 商品更新
   *
   * @param id 商品ID
   * @param item 更新する商品情報1件
   * @return item
   */
  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product update(@PathVariable Long id, @RequestBody @Validated Product item) {
    return itemService.update(id, item);
  }

  /**
   * 商品削除
   *
   * @param id 商品ID
   */
  @DeleteMapping("{id}")
  // 204
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    itemService.delete(id);
  }

  /**
   * 画像の取得
   *
   * @param id 商品ID
   * @return 画像データ HttpEntity<byte[]>
   */
  @GetMapping("{id}/images")
  @ResponseStatus(HttpStatus.OK)
  public HttpEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
    return itemService.getImage(id);
  }

  /**
   * 商品画像アップロード
   *
   * @param id 商品ID
   * @param file アップロードファイル
   * @return item
   */
  @PatchMapping("{id}/images")
  @ResponseStatus(HttpStatus.OK)
  public Product uploadImage(
      @PathVariable Long id, @RequestParam(name = "productImage") MultipartFile file) {
    return itemService.uploadImage(id, file);
  }

  /**
   * 画像情報の削除
   *
   * @param id 商品ID
   */
  @DeleteMapping("{id}/images")
  @ResponseStatus(HttpStatus.OK)
  public Product deleteImage(@PathVariable Long id) {
    return itemService.deleteImage(id);
  }
}
