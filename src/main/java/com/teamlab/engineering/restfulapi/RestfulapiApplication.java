package com.teamlab.engineering.restfulapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestfulapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestfulapiApplication.class, args);
  }
}
