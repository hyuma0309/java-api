package com.teamlab.engineering.restfulapi.controller;

import com.teamlab.engineering.restfulapi.service.OAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * OAuthコントローラー
 *
 * @author asada
 */
@Controller
public class OAuthController {
  private final OAuthService oAuthService;
  private final HttpSession httpSession;

  public OAuthController(OAuthService oAuthService, HttpSession httpSession) {
    this.oAuthService = oAuthService;
    this.httpSession = httpSession;
  }

  @GetMapping(path = "/")
  public String index() {
    return "index";
  }

  @GetMapping(path = "/logout")
  public String logout() {
    httpSession.invalidate();
    return "redirect:/";
  }
}
