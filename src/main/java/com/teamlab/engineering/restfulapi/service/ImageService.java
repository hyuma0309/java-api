package com.teamlab.engineering.restfulapi.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.teamlab.engineering.restfulapi.config.AwsConfiguration;
import com.teamlab.engineering.restfulapi.exception.UnsupportedMediaTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
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
class ImageService {

  private final AwsConfiguration awsConfiguration;

  @Value("${aws.bucket.name}")
  private String awsBucketName;

  ImageService(AwsConfiguration awsConfiguration) {
    this.awsConfiguration = awsConfiguration;
  }

  /**
   * 画像ファイルのアップロード
   *
   * @param multipartFile 画像ファイル
   * @return
   */
  String uploadFile(MultipartFile multipartFile) {
    // アップロードファイルのファイル名。
    String multipartFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    //  substring()は指定された位置の文字から最後までを切り取り、新しい文字列として返すメソッド
    //  lastIndexOf()は指定された要素をリスト内でindexから後向きに検索して最後に検出された位置のインデックスを返すメソッド
    String extension = multipartFileName.substring(multipartFileName.lastIndexOf("."));
    // 拡張子チェック
    validateExtension(multipartFile);
    // 偽装チェック
    validateMineType(multipartFile);
    String random = UUID.randomUUID().toString();
    String imagePath = random + extension;
    // S3クライアント作成
    AmazonS3 client = getClient();
    ObjectMetadata MetaData = new ObjectMetadata();

    try {
      // 画像データ取得
      byte[] decode = multipartFile.getBytes();
      try (InputStream inputStream = new ByteArrayInputStream(decode)) {
        client.putObject(awsBucketName, imagePath, inputStream, MetaData);
      } catch (AmazonServiceException e) {
        throw new RuntimeException("画像登録に失敗しました", e);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return imagePath;
  }

  void validateExtension(MultipartFile multipartFile) {
    String multipartFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    String extension = multipartFileName.substring(multipartFileName.lastIndexOf("."));
    if (!".jpeg".equalsIgnoreCase(extension)
        && !".jpg".equalsIgnoreCase(extension)
        && !".gif".equalsIgnoreCase(extension)
        && !".png".equalsIgnoreCase(extension)) {
      throw new UnsupportedMediaTypeException("未対応の拡張子です");
    }
  }

  void validateMineType(MultipartFile multipartFile) {
    String mimeType = multipartFile.getContentType();
    String realMimeType;

    try (InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream())) {
      // リクエストの本文の MIME タイプを返します
      realMimeType = URLConnection.guessContentTypeFromStream(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (!Objects.equals(realMimeType, mimeType)) {
      throw new UnsupportedMediaTypeException("ファイルが偽装されています");
    }
  }

  /**
   * 画像ファイルの削除
   *
   * @param imagePath 商品画像
   */
  void deleteFile(String imagePath) {
    AmazonS3 client = getClient();
    try {
      client.deleteObject(new DeleteObjectRequest(awsBucketName, imagePath));
    } catch (AmazonServiceException e) {
      throw new RuntimeException("画像削除に失敗しました", e);
    }
  }

  /** AWSクライアント作成 */
  private AmazonS3 getClient() {
    return awsConfiguration.getClient();
  }
}
