package com.teamlab.engineering.restfulapi.service;

import com.teamlab.engineering.restfulapi.dto.ProductDto;
import com.teamlab.engineering.restfulapi.entitiy.Product;
import com.teamlab.engineering.restfulapi.exception.AlreadyExistTitleException;
import com.teamlab.engineering.restfulapi.exception.ProductNotFoundException;
import com.teamlab.engineering.restfulapi.exception.ProductNotImageException;
import com.teamlab.engineering.restfulapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品サービス
 *
 * @author asada
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

  private final ProductRepository productRepository;

  private final ImageService imageService;

  private final ResourceLoader resourceLoader;

  @Value("${uploadDir}")
  private String uploadDir;

  /**
   * IDで商品取得
   *
   * @param id 商品ID
   * @return 取得した商品情報
   */
  public Product findProduct(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
  }

  /**
   * 商品情報登録
   *
   * @param title 商品タイトル
   * @param description 商品説明分
   * @param price 商品価格
   * @param api
   * @return 登録した商品情報
   */
  public Product create(String title, String description, Integer price) {
    if (isSameTitleExist(title)) {
      throw new AlreadyExistTitleException("既にその" + title + "は存在しています");
    }
    Product product = new Product();
    product.setTitle(title);
    product.setDescription(description);
    product.setPrice(price);
    return productRepository.save(product);
  }

  /**
   * 商品情報更新
   *
   * @param id 商品ID
   * @param title 商品タイトル
   * @param description 商品説明文
   * @param price 商品価格
   * @return 更新した商品情報
   */
  public Product update(long id, String title, String description, Integer price) {
    if (isSameTitleExistNotId(title, id)) {
      throw new AlreadyExistTitleException("既にその" + title + "は存在しています");
    }
    Product product = findProduct(id);
    product.setTitle(title);
    product.setDescription(description);
    product.setPrice(price);
    return productRepository.save(product);
  }

  /**
   * タイトルで商品検索
   *
   * @param title 商品タイトル
   * @return 検索結果の商品情報リスト
   */
  public List<ProductDto> search(String title) {
    return convertToProductDtoList(productRepository.findByTitleContaining(title));
  }

  /**
   * 商品全検索
   *
   * @return 商品情報
   */
  public List<ProductDto> getAllProducts() {
    return convertToProductDtoList(productRepository.findAllByOrderByUpdateTimeDesc());
  }

  /**
   * 商品削除
   *
   * @param id 商品ID
   */
  public void delete(Long id) {
    productRepository.delete(findProduct(id));
  }

  /**
   * 商品画像更新
   *
   * @param id 商品ID
   * @param file 商品画像
   * @return 画像を更新した商品情報
   */
  public Product uploadImage(Long id, MultipartFile file) {
    Product product = findProduct(id);
    //      Objects.nonNull＝指定された参照がnull以外の場合はtrueを返す。それ以外の場合はfalseを返す。
    //      既に画像が登録されてる場合、上書きするために削除
    if (Objects.nonNull(product.getImagePath())) {
      imageService.deleteFile(product.getImagePath());
    }
    product.setImagePath(imageService.uploadFile(file));
    return productRepository.save(product);
  }

  /**
   * 商品削除
   *
   * @param id 商品ID
   * @return 画像を削除した商品情報
   */
  public Product deleteImage(Long id) {
    Product product = findProduct(id);
    imageService.deleteFile(product.getImagePath());
    //  指定されたidの商品のimageをnullにして、画像を削除する.
    product.setImagePath(null);
    return productRepository.save(product);
  }

  /**
   * 商品画像取得
   *
   * @param id 商品ID
   * @return 商品画像
   */
  public HttpEntity<byte[]> getImage(Long id, String imagePath) throws IOException {
    Product product = findProduct(id);
    if (product.getImagePath() == null) {
      throw new ProductNotImageException("画像が存在しません");
    }
    Resource resource = resourceLoader.getResource("File:" + uploadDir + imagePath);
    byte[] b;
    // ResourceインタフェースはInputStreamSourceインタフェースを継承しているのでgetInputStreamメソッドで、リソースファイルのInputStreamを取得することができます。
    try (InputStream image = resource.getInputStream()) {
      // IOUtils型はInputStreamを読み込み、byte[]を返す静的メソッドを持ちます。
      b = IOUtils.toByteArray(image);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    headers.setContentLength(b.length);
    return new HttpEntity<>(b, headers);
  }

  /**
   * 同一タイトルの商品が存在するかを検証
   *
   * @param title 商品タイトル
   * @param api
   * @return 検証結果
   */
  private boolean isSameTitleExist(String title) {
    Product product = productRepository.findByTitleEquals(title);
    return product != null;
  }

  /**
   * IDが異なる同一タイトルの商品が存在するかを検証
   *
   * @param title 商品タイトル
   * @param id 商品 id
   * @param api
   * @return 検証結果
   */
  private boolean isSameTitleExistNotId(String title, long id) {
    Product product = productRepository.findByTitleEqualsAndIdNot(title, id);
    return product != null;
  }

  /**
   * 商品情報の格納
   *
   * @param productEntityList 商品情報
   * @return 商品情報
   */
  public List<ProductDto> convertToProductDtoList(List<Product> productEntityList) {
    return productEntityList.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  /**
   * entityからdtoへ変換する
   *
   * @param product Product
   * @return dto ProductDto
   */
  public ProductDto convertToDto(Product product) {
    return new ProductDto(product);
  }
}
