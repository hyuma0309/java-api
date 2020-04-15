package com.teamlab.engineering.restfulapi.config;

import Intercepter.OAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;

/**
 * OAuthInterceptor用のConfigクラス
 *
 * @author asada
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private HttpSession httpSession;

  @Bean
  public OAuthInterceptor oauthInterceptor() {
    return new OAuthInterceptor(httpSession);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(oauthInterceptor()).addPathPatterns("/github");
  }
}
