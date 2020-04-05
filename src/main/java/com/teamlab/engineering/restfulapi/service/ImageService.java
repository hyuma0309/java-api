package com.teamlab.engineering.restfulapi.service;

import com.teamlab.engineering.restfulapi.exception.ProductImageNotDeletedException;
import com.teamlab.engineering.restfulapi.exception.UnsupportedMediaTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Objects;
import java.util.UUID;

/**
 * 画像サービス
 *
 * @author asada
 */
@Service
@Transactional
class ImageService {

  // UploadDirプラグインはファイルのアップロード時に、ファイルの拡張子によってアップロード先のディレクトリを自動的に切り替えるプラグイン
  @Value("${uploadDir}")
  private String uploadDir;

  /**
   * 画像ファイルのアップロード
   *
   * @param multipartFile 画像ファイル
   * @return 商品画像
   */
  String uploadFile(MultipartFile multipartFile) {
    // アップロードファイルのファイル名。
    String multipartFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    //  substring()は指定された位置の文字から最後までを切り取り、新しい文字列として返すメソッド
    //  lastIndexOf()は指定された要素をリスト内でindexから後向きに検索して最後に検出された位置のインデックスを返すメソッド
    String extension = multipartFileName.substring(multipartFileName.lastIndexOf("."));
    // 拡張子チェック
    if (!".jpeg".equalsIgnoreCase(extension)
        && !".jpg".equalsIgnoreCase(extension)
        && !".gif".equalsIgnoreCase(extension)
        && !".png".equalsIgnoreCase(extension)) {
      throw new UnsupportedMediaTypeException("未対応の拡張子です");
    }
    try {
      // リクエストの本文の MIME タイプを返します
      String mimeType = multipartFile.getContentType();

      String realMimeType;
      try (InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream())) {
        realMimeType = URLConnection.guessContentTypeFromStream(inputStream);
      }
      if (!Objects.equals(realMimeType, mimeType)) {
        throw new UnsupportedMediaTypeException("ファイルが偽装されています");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String random = UUID.randomUUID().toString();
    String imagePath = random + extension;
    File file = new File(uploadDir + imagePath);
    try {
      multipartFile.transferTo(file.toPath());
      return imagePath;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 画像ファイルの削除
   *
   * @param imagePath 商品画像
   */
  void deleteFile(String imagePath) {
    File file = new File(uploadDir + imagePath);
    if (!file.delete()) {
      throw new ProductImageNotDeletedException("商品画像の削除に失敗しました: " + uploadDir + imagePath);
    }
  }
}
