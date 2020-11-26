package com.teamlab.engineering.restfulapi.filter;

import com.teamlab.engineering.restfulapi.setting.FrontSetting;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CorsFilter
 *
 * @author asada
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

  private final FrontSetting frontSetting;

  public CorsFilter(FrontSetting frontSetting) {
    this.frontSetting = frontSetting;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    response.setHeader("Access-Control-Allow-Origin", frontSetting.getUrl());
    response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-type");
    response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");

    if ("OPTIONS".equals(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      return;
    }
    // フィルタとしての処理を終了
    filterChain.doFilter(request, response);
  }
}
