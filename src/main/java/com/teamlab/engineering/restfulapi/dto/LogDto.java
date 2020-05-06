package com.teamlab.engineering.restfulapi.dto;

import java.time.LocalDate;

public class LogDto {
  private Long id;

  private String apiName;

  private String httpMethod;

  private String httpStatusCode;

  private Long accessTimes;

  private Double averageExecutionTime;

  private LocalDate aggregateDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getHttpStatusCode() {
    return httpStatusCode;
  }

  public void setHttpStatusCode(String httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }

  public Long getAccessTimes() {
    return accessTimes;
  }

  public void setAccessTimes(Long accessTimes) {
    this.accessTimes = accessTimes;
  }

  public Double getAverageExecutionTime() {
    return averageExecutionTime;
  }

  public void setAverageExecutionTime(Double averageExecutionTime) {
    this.averageExecutionTime = averageExecutionTime;
  }

  public LocalDate getAggregateDate() {
    return aggregateDate;
  }

  public void setAggregateDate(LocalDate aggregateDate) {
    this.aggregateDate = aggregateDate;
  }

  public LogDto(
      String apiName,
      String httpMethod,
      String httpStatusCode,
      Long accessTimes,
      Double averageExecutionTime,
      LocalDate aggregateDate) {
    id = id;
    this.apiName = apiName;
    this.httpMethod = httpMethod;
    this.httpStatusCode = httpStatusCode;
    this.accessTimes = accessTimes;
    this.averageExecutionTime = averageExecutionTime;
    this.aggregateDate = aggregateDate;
  }
}
