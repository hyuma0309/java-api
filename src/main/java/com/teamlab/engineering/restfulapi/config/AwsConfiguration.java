package com.teamlab.engineering.restfulapi.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client-configuration")
public class AwsConfiguration extends ClientConfiguration {

  @Value("${spring.profiles.active}")
  private String springProfilesActive;

  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;

  @Value("${cloud.aws.region.static}")
  private String region;

  @Value("${cloud.aws.endPointUrl}")
  private String endPointUrl;

  private int connectionTimeout;

  @Override
  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  @Override
  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  private int clientExecutionTimeout;

  @Override
  public int getClientExecutionTimeout() {
    return clientExecutionTimeout;
  }

  @Override
  public void setClientExecutionTimeout(int clientExecutionTimeout) {
    this.clientExecutionTimeout = clientExecutionTimeout;
  }

  private int requestTimeout;

  @Override
  public int getRequestTimeout() {
    return requestTimeout;
  }

  @Override
  public void setRequestTimeout(int requestTimeout) {
    this.requestTimeout = requestTimeout;
  }

  private int socketTimeout;

  @Override
  public int getSocketTimeout() {
    return socketTimeout;
  }

  @Override
  public void setSocketTimeout(int socketTimeout) {
    this.socketTimeout = socketTimeout;
  }

  private int maxErrorRetry;

  @Override
  public int getMaxErrorRetry() {
    return maxErrorRetry;
  }

  @Override
  public void setMaxErrorRetry(int maxErrorRetry) {
    this.maxErrorRetry = maxErrorRetry;
  }

  private Protocol protocol;

  @Override
  public Protocol getProtocol() {
    return protocol;
  }

  @Override
  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }

  private boolean useGzip;

  public boolean getUseGzip() {
    return useGzip;
  }

  @Override
  public void setUseGzip(boolean useGzip) {
    this.useGzip = useGzip;
  }

  @Bean
  public AmazonS3 getClient() {
    AwsConfiguration client = new AwsConfiguration();
    client.setProtocol(protocol);
    client.setConnectionTimeout(connectionTimeout);
    client.setClientExecutionTimeout(clientExecutionTimeout);
    client.setRequestTimeout(requestTimeout);
    client.setSocketTimeout(socketTimeout);
    client.setMaxErrorRetry(maxErrorRetry);
    client.setUseGzip(useGzip);

    AmazonS3ClientBuilder clientBuilder =
        AmazonS3ClientBuilder.standard()
            .withClientConfiguration(client)
            .withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(endPointUrl, region));

    // 環境によってキー認証かIAMロール認証か切り分ける
    if (isLocal()) {
      // ローカル環境の場合のみaccessKey・secretKeyで認証する
      AWSCredentials aWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
      AWSCredentialsProvider aWSCredentialsProvider =
          new AWSStaticCredentialsProvider(aWSCredentials);
      return clientBuilder.withCredentials(aWSCredentialsProvider).build();
    }
    // AWS環境の場合はIAMロール認証する
    return clientBuilder.build();
  }

  private boolean isLocal() {
    return "local".equalsIgnoreCase(springProfilesActive);
  }
}
