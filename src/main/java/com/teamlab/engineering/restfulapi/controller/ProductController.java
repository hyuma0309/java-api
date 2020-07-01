package com.teamlab.engineering.restfulapi.controller;

import com.teamlab.engineering.restfulapi.dto.ProductDto;
import com.teamlab.engineering.restfulapi.exception.ProductNotImageException;
import com.teamlab.engineering.restfulapi.form.ProductForm;
import com.teamlab.engineering.restfulapi.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("api/products")
public class ProductController {
  private final ProductService productService;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * 商品検索
   *
   * @param title 検索キーワード
   * @return 検索結果
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductDto> search(@RequestParam(required = false) String title) {
    if (StringUtils.isBlank(title)) {
      return productService.getAllProducts();
    }
    return productService.search(title);
  }

  /**
   * 商品登録
   *
   * @param productForm 商品登録フォーム
   * @return 商品情報登録
   */
  @PostMapping
  // HTTPステータスとして、”201 Created”を設定するため、value属性にはHttpStatus.CREATEDを設定する。
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto create(@RequestBody @Validated ProductForm ProductForm) {
    return productService.create(
        ProductForm.getTitle(), ProductForm.getDescription(), ProductForm.getPrice());
  }

  /**
   * 商品1件取得
   *
   * @param id 商品ID
   * @return 商品情報1件
   */
  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto show(@PathVariable long id) {
    return productService.getProduct(id);
  }

  /**
   * 商品更新
   *
   * @param id 商品ID
   * @param ProductForm 更新する商品情報1件
   * @return 更新する商品情報
   */
  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto update(@PathVariable Long id, @RequestBody @Validated ProductForm ProductForm) {
    return productService.update(
        id, ProductForm.getTitle(), ProductForm.getDescription(), ProductForm.getPrice());
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
    productService.delete(id);
  }

  /**
   * 画像の取得
   *
   * @param id 商品ID
   * @param imagePath 商品画像パス
   * @return 商品画像
   */
  @GetMapping("{id}/images/{imagePath}")
  @ResponseStatus(HttpStatus.OK)
  public HttpEntity<byte[]> getImage(@PathVariable Long id, @PathVariable String imagePath)
      throws IOException {
    try {
      return productService.getImage(id, imagePath);
    } catch (IOException e) {
      throw new ProductNotImageException("画像が存在しません", e);
    }
  }

  /**
   * 商品画像アップロード
   *
   * @param id 商品ID
   * @param file アップロードファイル
   * @return 画像を更新した商品情報
   */
  @PatchMapping("{id}/images")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto uploadImage(
      @PathVariable Long id, @RequestParam(name = "productImage") MultipartFile file) {
    return productService.uploadImage(id, file);
  }
}
