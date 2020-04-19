package com.teamlab.engineering.restfulapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config.access-token")
public class AccessTokenConfig {

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
