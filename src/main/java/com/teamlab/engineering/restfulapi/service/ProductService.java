package com.teamlab.engineering.restfulapi.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.teamlab.engineering.restfulapi.dto.ProductDto;
import com.teamlab.engineering.restfulapi.entitiy.Product;
import com.teamlab.engineering.restfulapi.exception.AlreadyExistTitleException;
import com.teamlab.engineering.restfulapi.exception.ProductNotFoundException;
import com.teamlab.engineering.restfulapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品サービス
 *
 * @author asada
 */
@Service
public class ProductService {

  private final ProductRepository productRepository;

  private final ImageService imageService;

  private final ResourceLoader resourceLoader;

  private final String awsBucketName = "tle-dev-asadahyuma";

  @Value("${uploadDir}")
  private String uploadDir;

  public ProductService(
      ProductRepository productRepository,
      ImageService imageService,
      ResourceLoader resourceLoader) {
    this.productRepository = productRepository;
    this.imageService = imageService;
    this.resourceLoader = resourceLoader;
  }

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
   * 商品IDで商品を取得する
   *
   * @param id 商品ID
   * @return 取得した商品情報DTO
   */
  public ProductDto getProduct(Long id) {
    return convertToDto(findProduct(id));
  }
  /**
   * 商品情報登録
   *
   * @param api
   * @param title 商品タイトル
   * @param description 商品説明分
   * @param price 商品価格
   * @return 登録した商品情報
   */
  public ProductDto create(String title, String description, Integer price) {
    if (isSameTitleExist(title)) {
      throw new AlreadyExistTitleException("既にその" + title + "は存在しています");
    }
    Product product = new Product();
    product.setTitle(title);
    product.setDescription(description);
    product.setPrice(price);
    return convertToDto(productRepository.save(product));
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
  public ProductDto update(long id, String title, String description, Integer price) {
    if (isSameTitleExistNotId(title, id)) {
      throw new AlreadyExistTitleException("既にその" + title + "は存在しています");
    }
    Product product = findProduct(id);
    product.setTitle(title);
    product.setDescription(description);
    product.setPrice(price);
    return convertToDto(productRepository.save(product));
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
    Product product = findProduct(id);
    if (Objects.nonNull(product.getImagePath())) {
      imageService.deleteFile(product.getImagePath());
    }
    productRepository.delete(product);
  }

  /**
   * 商品画像更新
   *
   * @param id 商品ID
   * @param file 商品画像
   * @return 画像を更新した商品情報
   */
  public ProductDto uploadImage(Long id, MultipartFile file) {
    Product product = findProduct(id);
    //      Objects.nonNull＝指定された参照がnull以外の場合はtrueを返す。それ以外の場合はfalseを返す。
    //      既に画像が登録されてる場合、上書きするために削除
    if (Objects.nonNull(product.getImagePath())) {
      imageService.deleteFile(product.getImagePath());
    }
    product.setImagePath(imageService.uploadFile(file));
    return convertToDto(productRepository.save(product));
  }

  /**
   * 商品画像取得
   *
   * @param id 商品ID
   * @return 商品画像
   */
  public HttpEntity<byte[]> getImage(Long id, String imagePath) throws IOException {
    Product product = findProduct(id);
    AmazonS3 client = getClient();

    try (S3Object s3Image = client.getObject(awsBucketName, imagePath)) {
      byte[] imageByte = s3Image.getObjectContent().readAllBytes();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentLength(imageByte.length);
      return new HttpEntity<>(imageByte, headers);
    } catch (IOException | AmazonServiceException e) {
      throw new RuntimeException("画像取得に失敗しました", e);
    }
  }

  /**
   * 同一タイトルの商品が存在するかを検証
   *
   * @param api
   * @param title 商品タイトル
   * @return 検証結果
   */
  public boolean isSameTitleExist(String title) {
    return productRepository.findByTitleEquals(title).isPresent();
  }

  /**
   * IDが異なる同一タイトルの商品が存在するかを検証
   *
   * @param api
   * @param title 商品タイトル
   * @param id 商品 id
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

  /** AWSクライアント作成 */
  private AmazonS3 getClient() {
    return AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1).build();
  }
}
