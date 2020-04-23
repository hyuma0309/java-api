package com.teamlab.engineering.restfulapi.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AccessTokenの設定
 *
 * @author asada
 */
@Component
@ConfigurationProperties(prefix = "config.access-token")
public class AccessTokenSetting {

  private long usableMinutes;

  private String authorizationHeaderFormat;

  public long getUsableMinutes() {
    return usableMinutes;
  }

  public void setUsableMinutes(long usableMinutes) {
    this.usableMinutes = usableMinutes;
  }

  public String getAuthorizationHeaderFormat() {
    return authorizationHeaderFormat;
  }

  public void setAuthorizationHeaderFormat(String authorizationHeaderFormat) {
    this.authorizationHeaderFormat = authorizationHeaderFormat;
  }
}
