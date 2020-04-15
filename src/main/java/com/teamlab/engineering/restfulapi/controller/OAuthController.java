package com.teamlab.engineering.restfulapi.controller;

import com.teamlab.engineering.restfulapi.dto.GithubDto;
import com.teamlab.engineering.restfulapi.service.OAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * OAuthコントローラー
 *
 * @author asada
 */
@Controller
public class OAuthController {
  private static final String TOKEN = "token";

  private final OAuthService oAuthService;
  private final HttpSession httpSession;

  public OAuthController(OAuthService oAuthService, HttpSession httpSession) {
    this.oAuthService = oAuthService;
    this.httpSession = httpSession;
  }

  /**
   * ログイン画面表示
   *
   * @return templates/index.html
   */
  @GetMapping("/")
  public String index() {
    return "index";
  }

  /**
   * Githubを使用したログイン
   *
   * @return Githubのログイン認証
   */
  @GetMapping("/github/login")
  public String login() {
    return "redirect:" + oAuthService.getGithubAuthorizeUrl();
  }

  /**
   * ログイン認証後のコールバック
   *
   * @param code 認証コード
   * @return /github　githubプロフィール画面
   */
  @GetMapping("/github/callback")
  public String callback(String code) {
    if (code == null || code.isEmpty()) {
      return "error/404";
    }
    String token = oAuthService.getGithubAccessToken(code);
    // サーバ側にデータを保存
    httpSession.setAttribute("token", token);
    httpSession.setAttribute("isLogin", true);
    return "redirect:/github";
  }

  /**
   * Githubプロフィール画面の表示
   *
   * @param model model
   * @return profile.html
   */
  @GetMapping("/github")
  public String profile(Model model) {
    GithubDto githubUserProfile =
        oAuthService.getGithubUserProfile(httpSession.getAttribute("token").toString());
    model.addAttribute("githubUserProfile", githubUserProfile);
    return "profile";
  }

  /**
   * ログアウト
   *
   * @return index.html
   */
  @GetMapping("/logout")
  public String logout() {
    // セッションを破棄
    httpSession.invalidate();
    return "redirect:/";
  }
}
