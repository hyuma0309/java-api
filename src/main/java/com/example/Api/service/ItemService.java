package com.example.Api.service;

import com.example.Api.entitiy.Api;
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

  public List<Api> findAll() {
    return itemRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
  }

  public Api findItem(Long id) {
    return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
  }

  public Api create(Api api) {
    Api item = new Api();
    item.setName(api.getName());
    item.setDescription(api.getDescription());
    item.setPrice(api.getPrice());
    return itemRepository.save(item);
  }

  public Api update(Long id, Api api) {
    Api item = findItem(id);
    item.setName(api.getName());
    item.setDescription(api.getDescription());
    item.setPrice(api.getPrice());
    return itemRepository.save(item);
  }

  public List<Api> search(String keyword) {
    return itemRepository.findByNameContaining(keyword);
  }

  public void delete(Long id) {
    itemRepository.delete(findItem(id));
  }

  public Api uploadImage(Long id, MultipartFile file) {
    Api item = findItem(id);
    //      Objects.nonNull＝指定された参照がnull以外の場合はtrueを返す。それ以外の場合はfalseを返す。
    //      既に画像が登録されてる場合、上書きするために削除
    if (Objects.nonNull(item.getImage())) imageService.deleteFile(item.getImage());
    item.setImage(imageService.uploadFile(file));
    return itemRepository.save(item);
  }

  //  指定されたidの商品のimageをnullにして、画像を削除する.
  public Api deleteImage(Long id) {
    Api item = findItem(id);
    imageService.deleteFile(item.getImage());
    item.setImage(null);
    return itemRepository.save(item);
  }

  public HttpEntity<byte[]> getImage(Long id) throws IOException {
    Api item = findItem(id);
    Resource resource = resourceLoader.getResource("File:" + uploadDir + item.getImage());
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
}
