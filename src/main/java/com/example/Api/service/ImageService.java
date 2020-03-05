package com.example.Api.service;

import com.example.Api.exception.ItemImageNotDeletedException;
import com.example.Api.exception.UnsupportedMediaTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
class ImageService {

  // UploadDirプラグインはファイルのアップロード時に、ファイルの拡張子によってアップロード先のディレクトリを自動的に切り替えるプラグイン
  @Value("${uploadDir}")
  private String uploadDir;

  String uploadFile(MultipartFile multipartFile) {
    // アップロードファイルのファイル名。
    String multipartFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    //  substring()は指定された位置の文字から最後までを切り取り、新しい文字列として返すメソッド
    //  lastIndexOf()は指定された要素をリスト内でindexから後向きに検索して最後に検出された位置のインデックスを返すメソッド
    String extension = multipartFileName.substring(multipartFileName.lastIndexOf("."));
    if (!".jpeg".equalsIgnoreCase(extension)
        && !".jpg".equalsIgnoreCase(extension)
        && !".gif".equalsIgnoreCase(extension)
        && !".png".equalsIgnoreCase(extension)) throw new UnsupportedMediaTypeException("未対応の拡張子です");
    String random = UUID.randomUUID().toString();
    String imagePath = "image/" + random + extension;
    File file = new File(uploadDir + imagePath);
    try {
      multipartFile.transferTo(file.toPath());
      return imagePath;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void deleteFile(String imagePath) {
    File file = new File(uploadDir + imagePath);
    if (!file.delete()) throw new ItemImageNotDeletedException("商品画像の削除に失敗しました: " + uploadDir + imagePath);
  }
}
