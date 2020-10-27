package com.teamlab.engineering.restfulapi.dto;

import com.amazonaws.Protocol;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client-configuration")
public class S3clientConfigDto {
  private int connectionTimeoutSec;
  private int clientExecutionTimeoutSec;
  private int requestTimeoutSec;
  private int socketTimeoutSec;
  private int maxErrorRetryCount;
  private Protocol protocol;
  private boolean useGzip;

  public int getConnectionTimeoutSec() {
    return connectionTimeoutSec;
  }

  public void setConnectionTimeoutSec(int connectionTimeoutSec) {
    this.connectionTimeoutSec = connectionTimeoutSec;
  }

  public int getClientExecutionTimeoutSec() {
    return clientExecutionTimeoutSec;
  }

  public void setClientExecutionTimeoutSec(int clientExecutionTimeoutSec) {
    this.clientExecutionTimeoutSec = clientExecutionTimeoutSec;
  }

  public int getRequestTimeoutSec() {
    return requestTimeoutSec;
  }

  public void setRequestTimeoutSec(int requestTimeoutSec) {
    this.requestTimeoutSec = requestTimeoutSec;
  }

  public int getSocketTimeoutSec() {
    return socketTimeoutSec;
  }

  public void setSocketTimeoutSec(int socketTimeoutSec) {
    this.socketTimeoutSec = socketTimeoutSec;
  }

  public int getMaxErrorRetryCount() {
    return maxErrorRetryCount;
  }

  public void setMaxErrorRetryCount(int maxErrorRetryCount) {
    this.maxErrorRetryCount = maxErrorRetryCount;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }

  public boolean getUseGzip() {
    return useGzip;
  }

  public void setUseGzip(boolean useGzip) {
    this.useGzip = useGzip;
  }
}
