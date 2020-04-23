package com.teamlab.engineering.restfulapi.repository;

import com.teamlab.engineering.restfulapi.entitiy.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

  /**
   * 指定された有効期限が切れたトークンを一括削除
   *
   * @param usableMinutes LocalDateTime
   */
  void deleteAccessTokenByUpdateTimeLessThan(LocalDateTime limitTime);

  /**
   * トークンレコード取得
   *
   * @param accessToken　accessToken
   * @return トークンレコード
   */
  @Transactional
  AccessToken findByAccessToken(String accessToken);
}
