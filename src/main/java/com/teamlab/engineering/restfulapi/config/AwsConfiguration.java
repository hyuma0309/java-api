package com.teamlab.engineering.restfulapi.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.teamlab.engineering.restfulapi.setting.S3ClientConfigSetting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfiguration {

  private final S3ClientConfigSetting s3ClientConfigSetting;

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

  public AwsConfiguration(S3ClientConfigSetting s3ClientConfigSetting) {
    this.s3ClientConfigSetting = s3ClientConfigSetting;
  }

  @Bean
  public AmazonS3 getClient() {
    ClientConfiguration client = new ClientConfiguration();
    client.setProtocol(s3ClientConfigSetting.getProtocol());
    client.setConnectionTimeout(s3ClientConfigSetting.getConnectionTimeoutSec());
    client.setClientExecutionTimeout(s3ClientConfigSetting.getClientExecutionTimeoutSec());
    client.setRequestTimeout(s3ClientConfigSetting.getRequestTimeoutSec());
    client.setSocketTimeout(s3ClientConfigSetting.getSocketTimeoutSec());
    client.setMaxErrorRetry(s3ClientConfigSetting.getMaxErrorRetryCount());
    client.setUseGzip(s3ClientConfigSetting.getUseGzip());

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
