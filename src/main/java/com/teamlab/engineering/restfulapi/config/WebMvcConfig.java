package com.teamlab.engineering.restfulapi.config;

import com.teamlab.engineering.restfulapi.interceptor.OAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * OAuthInterceptor用のConfigクラス
 *
 * @author asada
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Bean
  public OAuthInterceptor oauthInterceptor() {
    return new OAuthInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(oauthInterceptor()).addPathPatterns("/github");
  }
}
