package com.teamlab.engineering.restfulapi.config;

import com.teamlab.engineering.restfulapi.filter.AccessTokenFilter;
import com.teamlab.engineering.restfulapi.filter.CorsFilter;
import com.teamlab.engineering.restfulapi.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * FilterConfigurationクラス
 *
 * @author asada
 */
@Configuration
public class FilterConfiguration {
  private final AccessTokenFilter accessTokenFilter;
  private final LogFilter logFilter;
  private final CorsFilter corsFilter;

  public FilterConfiguration(
      AccessTokenFilter accessTokenFilter, LogFilter logFilter, CorsFilter corsFilter) {
    this.accessTokenFilter = accessTokenFilter;
    this.logFilter = logFilter;
    this.corsFilter = corsFilter;
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
    bean.setOrder(Ordered.LOWEST_PRECEDENCE);
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
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
    return bean;
  }

  /**
   * Cors用のbean定義
   *
   * @return FilterRegistrationBean
   */
  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilterFilterFilterRegistrationBean() {
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(corsFilter);
    bean.addUrlPatterns("/api/products/*");
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }
}
