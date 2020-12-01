package com.teamlab.engineering.restfulapi.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Corsの設定
 *
 * @author asada
 */
@Component
@ConfigurationProperties(prefix = "frontend")
public class FrontSetting {
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
