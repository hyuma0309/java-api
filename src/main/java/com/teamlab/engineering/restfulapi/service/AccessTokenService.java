package com.teamlab.engineering.restfulapi.service;

import com.teamlab.engineering.restfulapi.entitiy.AccessToken;
import com.teamlab.engineering.restfulapi.repository.AccessTokenRepository;
import com.teamlab.engineering.restfulapi.setting.AccessTokenSetting;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

/**
 * アクセストークンService
 *
 * @author asada
 */
@Transactional
@Service
public class AccessTokenService {
  private final AccessTokenRepository accessTokenRepository;
  private final AccessTokenSetting accessTokenConfig;

  public AccessTokenService(
      AccessTokenRepository accessTokenRepository, AccessTokenSetting accessTokenConfig) {
    this.accessTokenRepository = accessTokenRepository;
    this.accessTokenConfig = accessTokenConfig;
  }

  /**
   * アクセストークンの生成
   *
   * @return アクセストークン
   */
  private String createAccessToken() {
    byte[] accessTokenBytes = UUID.randomUUID().toString().getBytes();
    return Base64.getEncoder().encodeToString(accessTokenBytes);
  }

  /**
   * アクセストークンの保存
   *
   * @return アクセストークン
   */
  public AccessToken saveAccessToken() {
    AccessToken accessToken = new AccessToken();
    accessToken.setAccessToken(createAccessToken());
    return accessTokenRepository.save(accessToken);
  }

  /** 有効期限切れのアクセストークン削除 */
  public void deleteApiToken() {
    LocalDateTime limitTime =
        LocalDateTime.now().minusMinutes(accessTokenConfig.getUsableMinutes());
    accessTokenRepository.deleteAccessTokenByUpdateTimeLessThan(limitTime);
  }

  /**
   * アクセストークンがDBに存在するかを確認
   *
   * @param accessToken
   * @return アクセストークン
   */
  public AccessToken getAccessToken(String accessToken) {
    return accessTokenRepository.findByAccessToken(accessToken);
  }

  /**
   * アクセストークンの有効期限を確認
   *
   * @param accessToken アクセストークン
   * @return 有効期限内:true, 有効期限切れ:false
   */
  public boolean isAccessTokenDeadlineEnabled(AccessToken accessToken) {
    return accessToken
        .getUpdateTime()
        .isBefore(LocalDateTime.now().minusMinutes(accessTokenConfig.getUsableMinutes()));
  }

  /**
   * アクセストークンの有効期限を現在時間に更新
   *
   * @param accessToken アクセストークン
   */
  public void overWriteAccessTokenCurrentTimeUpdate(AccessToken accessToken) {
    accessToken.setUpdateTime(LocalDateTime.now());
    accessTokenRepository.save(accessToken);
  }
}
