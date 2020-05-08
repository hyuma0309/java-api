package com.teamlab.engineering.restfulapi.config;

import com.teamlab.engineering.restfulapi.filter.AccessTokenFilter;
import com.teamlab.engineering.restfulapi.filter.LogFilter;
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
  private final LogFilter logFilter;

  public FilterConfiguration(AccessTokenFilter accessTokenFilter, LogFilter logFilter) {
    this.accessTokenFilter = accessTokenFilter;
    this.logFilter = logFilter;
  }

  /**
   * AccessTokenFilterのbean定義
   *
   * @return FilterRegistrationBean
   */
  @Bean
  public FilterRegistrationBean<AccessTokenFilter> accessTokenFilterFilterRegistrationBean() {
    FilterRegistrationBean<AccessTokenFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(accessTokenFilter);
    bean.addUrlPatterns("/api/products/*");
    bean.setOrder(Integer.MIN_VALUE + 1);
    return bean;
  }

  /**
   * アクセスログ用のbean定義
   *
   * @return FilterRegistrationBean
   */
  @Bean
  public FilterRegistrationBean<LogFilter> logFilterFilterRegistrationBean() {
    FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(logFilter);
    bean.addUrlPatterns("/api/products/*");
    bean.setOrder(Integer.MIN_VALUE);
    return bean;
  }
}
