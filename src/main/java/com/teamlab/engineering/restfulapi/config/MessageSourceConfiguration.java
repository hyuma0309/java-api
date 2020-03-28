package com.teamlab.engineering.restfulapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * メッセージソースの設定
 *
 * @author asada
 */
@Configuration
@RequiredArgsConstructor
public class MessageSourceConfiguration implements WebMvcConfigurer {
  private final MessageSource messageSource;

  /**
   * メッセージソースの変更
   *
   * @return LocalValidatorFactoryBean
   */
  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setValidationMessageSource(messageSource);
    return localValidatorFactoryBean;
  }

  /**
   * getValidator()をオーバーライド
   *
   * @return org.springframework.validation.Validator
   */
  @Override
  public Validator getValidator() {
    return validator();
  }
}
