package com.teamlab.engineering.restfulapi.service;

import com.teamlab.engineering.restfulapi.dto.GithubDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubUserProfile;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

/**
 * OAuthサービス
 *
 * @author asada
 */
@Service
public class OAuthService {
  /** githubの設定 */
  @Value("${clientId}")
  private String client;

  @Value("${clientSecret}")
  private String secret;

  @Value("${callbackUrl}")
  private String callbackUrl;

  /**
   * 認証用URLの作成
   *
   * @return 認証用URL
   */
  public String getGithubAuthorizeUrl() {
    // 認証のためにユーザーをリダイレクトするURLを作成します。
    return operations().buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, new OAuth2Parameters());
  }

  /**
   * アクセストークンの取得
   *
   * @param code 認証コード
   * @return アクセストークン
   */
  public String getGithubAccessToken(String code) {
    // 認証コードをアクセス許可と交換します。
    return operations().exchangeForAccess(code, callbackUrl, null).getAccessToken();
  }

  /**
   * Githubユーザのプロフィールを取得し、Dtoに保存する処理
   *
   * @param token アクセストークン
   * @return ユーザのプロフィール情報
   */
  public GithubDto getGithubUserProfile(String token) {
    GitHub gitHub = new GitHubTemplate(token);
    GitHubUserProfile gitHubUserProfile = gitHub.userOperations().getUserProfile();
    return convertToGitHubUserDto(gitHubUserProfile);
  }

  /**
   * 取得したプロフィール情報をDtoに保存
   *
   * @param gitHubUserProfile ユーザのプロフィール情報
   * @return githubUserDto
   */
  private GithubDto convertToGitHubUserDto(GitHubUserProfile gitHubUserProfile) {
    return new GithubDto(gitHubUserProfile);
  }

  /**
   * Githubの接続処理
   *
   * @return OAuthOperations
   */
  private OAuth2Operations operations() {
    GitHubConnectionFactory gitHubConnectionFactory = new GitHubConnectionFactory(client, secret);
    // プロバイダーとOAuth2フローを実行できるようにする
    OAuth2Operations operations = gitHubConnectionFactory.getOAuthOperations();
    return operations;
  }
}
