package com.teamlab.engineering.restfulapi.config;

import com.teamlab.engineering.restfulapi.filter.AccessTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FilterConfigurationクラス
 *
 * @author asada
 */
@Configuration
public class FilterConfiguration {
  private final AccessTokenFilter accessTokenFilter;

  public FilterConfiguration(AccessTokenFilter accessTokenFilter) {
    this.accessTokenFilter = accessTokenFilter;
  }

  /**
   * ApiFilterのbean定義
   *
   * @return FilterRegistrationBean
   */
  @Bean
  public FilterRegistrationBean<AccessTokenFilter> authenticationFilterRegistration() {
    FilterRegistrationBean<AccessTokenFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(accessTokenFilter);
    bean.addUrlPatterns("/api/products/*");
    return bean;
  }
}
