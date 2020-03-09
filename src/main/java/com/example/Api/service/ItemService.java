package com.example.Api.service;

import com.example.Api.entitiy.Api;
import com.example.Api.exception.AlreadyExistTitleException;
import com.example.Api.exception.ItemNotFoundException;
import com.example.Api.repository.ItemRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
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

/**
 * 商品サービス
 *
 * @author asada
 */
@Service
@Transactional
public class ItemService {

  private final ItemRepository itemRepository;

  private final ImageService imageService;

  private final ResourceLoader resourceLoader;

  @Value("${uploadDir}")
  private String uploadDir;

  @Autowired
  public ItemService(
      ImageService imageService, ItemRepository itemRepository, ResourceLoader resourceLoader) {
    this.itemRepository = itemRepository;
    this.imageService = imageService;
    this.resourceLoader = resourceLoader;
  }

  /**
   * 全商品取得
   *
   * @return すべての商品情報リスト
   */
  public List<Api> findAll() {
    return itemRepository.findAll(new Sort(Sort.Direction.DESC, "UpdateTime"));
  }

  /**
   * IDで商品取得
   *
   * @param id 商品ID
   * @return 取得した商品情報
   */
  public Api findItem(Long id) {
    return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
  }

  /**
   * 商品情報登録
   *
   * @param title 商品タイトル
   * @param description 商品説明分
   * @param price 商品価格
   * @return 登録した商品情報
   */
  public Api create(Api api) {
    if (isSameTitleExist(api)) throw new AlreadyExistTitleException("既にその" + api.getTitle() + "は存在しています");
    Api item = new Api();
    item.setTitle(api.getTitle());
    item.setDescription(api.getDescription());
    item.setPrice(api.getPrice());
    return itemRepository.save(item);
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
  public Api update(Long id, Api api) {
    if (isSameTitleExistNotId(api)) throw new AlreadyExistTitleException("既にその" + api.getTitle() + "は存在しています");
    Api item = findItem(id);
    item.setTitle(api.getTitle());
    item.setDescription(api.getDescription());
    item.setPrice(api.getPrice());
    return itemRepository.save(item);
  }

  /**
   * タイトルで商品検索
   *
   * @param keyword 商品タイトル
   * @return 検索結果の商品情報リスト
   */
  public List<Api> search(String keyword) {
    return itemRepository.findByTitleContaining(keyword);
  }

  /**
   * 商品削除
   *
   * @param id 商品ID
   */
  public void delete(Long id) {
    itemRepository.delete(findItem(id));
  }

  /**
   * 商品画像更新
   *
   * @param id 商品ID
   * @param file 商品画像
   * @return 画像を更新した商品情報
   */
  public Api uploadImage(Long id, MultipartFile file) {
    Api item = findItem(id);
    //      Objects.nonNull＝指定された参照がnull以外の場合はtrueを返す。それ以外の場合はfalseを返す。
    //      既に画像が登録されてる場合、上書きするために削除
    if (Objects.nonNull(item.getImagepath())) imageService.deleteFile(item.getImagepath());
    item.setImagepath(imageService.uploadFile(file));
    return itemRepository.save(item);
  }

  /**
   * 商品削除
   *
   * @param id 商品ID
   * @return 画像を削除した商品情報
   */
  public Api deleteImage(Long id) {
    Api item = findItem(id);
    imageService.deleteFile(item.getImagepath());
    //  指定されたidの商品のimageをnullにして、画像を削除する.
    item.setImagepath(null);
    return itemRepository.save(item);
  }

  /**
   * 商品画像取得
   *
   * @param id 商品ID
   * @return 商品画像
   */
  public HttpEntity<byte[]> getImage(Long id) throws IOException {
    Api item = findItem(id);
    Resource resource = resourceLoader.getResource("File:" + uploadDir + item.getImagepath());
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
   * @return 検証結果
   */
  private boolean isSameTitleExist(Api api) {
    Api product = itemRepository.findByTitleEquals(api.getTitle());
    return product != null;
  }

  /**
   * IDが言葉る同一タイトルの商品が存在するかを検証
   *
   * @param title 商品タイトル
   * @param id 商品 id
   * @return 検証結果
   */
  private boolean isSameTitleExistNotId(Api api) {
    Api product = itemRepository.findByTitleEqualsAndIdNot(api.getTitle(), api.getId());
    return product != null;
  }
}
