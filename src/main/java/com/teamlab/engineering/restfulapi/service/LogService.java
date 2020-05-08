package com.teamlab.engineering.restfulapi.service;

import com.teamlab.engineering.restfulapi.constants.ApiName;
import com.teamlab.engineering.restfulapi.dto.LogDto;
import com.teamlab.engineering.restfulapi.entitiy.Log;
import com.teamlab.engineering.restfulapi.repository.LogRepository;
import com.teamlab.engineering.restfulapi.setting.LogSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogService {

  private final LogRepository logRepository;
  private final LogSetting logSetting;
  private static final Logger logger = LoggerFactory.getLogger(LogService.class);

  public LogService(LogRepository logRepository, LogSetting logSetting) {
    this.logRepository = logRepository;
    this.logSetting = logSetting;
  }

  /** 1日前のログファイル を集計,保存 */
  public void yesterdayAggregateFile() {
    LocalDate yesterday = LocalDate.now().minusDays(1);

    String logFileName = yesterday + logSetting.getExtension();
    File targetLogFile = new File(logFileName);

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(targetLogFile))) {
      logger.info("対象のログファイル[{}]を見つけました。読み込みます。", logFileName);

      Map<String, LogDto> logDtoMap = new HashMap<>();

      String logDataLine;

      while ((logDataLine = bufferedReader.readLine()) != null) {
        String[] logDataLineElements = logDataLine.split("\t");

        String httpMethod = logDataLineElements[0];
        String url = logDataLineElements[1];
        String httpStatusCode = logDataLineElements[2];
        String excutionTime = logDataLineElements[3];
        String aggregationDate = logDataLineElements[4];

        // API名に変換
        String apiName = String.valueOf(ApiName.getApiName(url, httpMethod));

        String dtoKey = apiName + httpMethod + httpStatusCode;

        addMapByLogMapKey(
            logDtoMap, dtoKey, apiName, httpMethod, aggregationDate, excutionTime, httpStatusCode);
      }

      List<LogDto> accessLogDtoList = new ArrayList<>(logDtoMap.values());

      logger.info("ログの集計結果をDBへ保存します。");
      saveAllLogs(accessLogDtoList);

      logger.info("ログの集計結果のDBへの保存が終了しました。");
    } catch (IOException e) {
      logger.error("ログの読み込みが正しく行われませんでした。", e);
    }
  }

  private void addMapByLogMapKey(
      Map<String, LogDto> logDtoMap,
      String dtoKey,
      String apiName,
      String httpMethod,
      String aggregationDate,
      String excutionTime,
      String httpStatusCode) {
    if (logDtoMap.containsKey(dtoKey)) {

      LogDto logDto = logDtoMap.get(dtoKey);

      var averageTime =
          ((logDto.getAverageExecutionTime() * logDto.getAccessTimes())
                  + Integer.parseInt(excutionTime))
              / (logDto.getAccessTimes() + 1);
      logDto.setAccessTimes(logDto.getAccessTimes() + 1);
      logDto.setAverageExecutionTime(averageTime);
      logDtoMap.put(dtoKey, logDto);
    } else {
      // アクセス回数
      long access_times = 1;

      logDtoMap.put(
          dtoKey,
          new LogDto(
              apiName,
              httpMethod,
              httpStatusCode,
              access_times,
              Double.parseDouble(excutionTime),
              LocalDate.parse(aggregationDate, DateTimeFormatter.ISO_LOCAL_DATE)));
    }
  }

  private void saveAllLogs(List<LogDto> apiAccessLogDtoList) {
    List<Log> apiAccessLogEntityList =
        apiAccessLogDtoList.stream().map(this::convertToEntity).collect(Collectors.toList());
    logRepository.saveAll(apiAccessLogEntityList);
  }

  private Log convertToEntity(LogDto logDto) {
    return new Log(logDto);
  }

  public List<Log> searchAggregate(LocalDate startDate, LocalDate endDate) {
    return logRepository.findByAggregateDateBetween(startDate, endDate);
  }
}
