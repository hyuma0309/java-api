package com.teamlab.engineering.restfulapi.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Corsの設定
 *
 * @author asada
 */
@Component
@ConfigurationProperties(prefix = "frontend.url")
public class FrontSetting {
  private List<String> urls;

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }
}
