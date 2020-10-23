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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfiguration {

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

  @Bean
  public AmazonS3 getClient() {
    ClientConfiguration clientConfig = new ClientConfiguration();
    clientConfig.setProtocol(Protocol.HTTPS);
    clientConfig.setConnectionTimeout(10000);
    clientConfig.setClientExecutionTimeout(10000);
    clientConfig.setRequestTimeout(10000);
    clientConfig.setSocketTimeout(10000);
    clientConfig.setMaxErrorRetry(5);
    clientConfig.setUseGzip(true);

    AmazonS3ClientBuilder clientBuilder =
        AmazonS3ClientBuilder.standard()
            .withClientConfiguration(clientConfig)
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
