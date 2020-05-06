package com.teamlab.engineering.restfulapi.entitiy;

import com.teamlab.engineering.restfulapi.dto.LogDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "logs")
public class Log {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String apiName;

  private String httpMethod;

  private String httpStatusCode;

  private Long accessTimes;

  private Double averageExecutionTime;

  private LocalDate aggregateDate;

  public Log(
      String apiName,
      String httpMethod,
      String httpStatusCode,
      Long accessTimes,
      Double averageExecutionTime,
      LocalDate aggregateDate) {}

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

  public Log(LogDto logDto) {
    id = logDto.getId();
    apiName = logDto.getApiName();
    httpMethod = logDto.getHttpMethod();
    httpStatusCode = logDto.getHttpStatusCode();
    accessTimes = logDto.getAccessTimes();
    aggregateDate = logDto.getAggregateDate();
    averageExecutionTime = logDto.getAverageExecutionTime();
  }
}
