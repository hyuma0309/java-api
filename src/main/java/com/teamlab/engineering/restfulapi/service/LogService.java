package com.teamlab.engineering.restfulapi.service;

import com.teamlab.engineering.restfulapi.dto.LogDto;
import com.teamlab.engineering.restfulapi.entitiy.Log;
import com.teamlab.engineering.restfulapi.repository.LogRepository;
import com.teamlab.engineering.restfulapi.setting.LogSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ログサービス
 *
 * @author asada
 */
@Service
public class LogService {

  private static final Pattern urlPattern = Pattern.compile("^/api/products");
  private static final Pattern httpMethodPattern = Pattern.compile("GET|POST|PUT|DELETE|PATCH");
  private static final Pattern httpStatusCodePattern = Pattern.compile("^[0-9]+$");
  private static final Pattern accessTimesPattern = Pattern.compile("^[0-9]+$");
  private static final Pattern aggregationDatePattern =
      Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");

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
      long logDataLineNumber = 0;

      while ((logDataLine = bufferedReader.readLine()) != null) {
        String[] logDataLineElements = logDataLine.split("\t");

        ++logDataLineNumber;

        if (logDataLineElements.length > 4) {
          String httpMethod = logDataLineElements[0];
          String url = logDataLineElements[1];
          String httpStatusCode = logDataLineElements[2];
          String excutionTime = logDataLineElements[3];
          String aggregationDate = logDataLineElements[4];

          // API名に変換

          String apiName = String.valueOf(ApiName.getApiName(url, httpMethod));

          if (!checkLogPattern(
              logDataLineNumber, url, httpMethod, httpStatusCode, excutionTime, aggregationDate)) {
            continue;
          }

          String dtoKey = apiName + httpMethod + httpStatusCode;

          addMapByLogMapKey(
              logDtoMap,
              dtoKey,
              apiName,
              httpMethod,
              aggregationDate,
              excutionTime,
              httpStatusCode);
        }
      }

      List<LogDto> accessLogDtoList = new ArrayList<>(logDtoMap.values());

      logger.info("ログの集計結果をDBへ保存します。");
      saveAllLogs(accessLogDtoList);

      logger.info("ログの集計結果のDBへの保存が終了しました。");
    } catch (IOException e) {
      logger.error("ログの読み込みが正しく行われませんでした。", e);
    }
  }

  /**
   * アクセスログをマップに保存
   *
   * @param logDtoMap
   * @param dtoKey マップのキー
   * @param apiName API名
   * @param httpMethod Httpメソッド
   * @param aggregationDate 集計日
   * @param excutionTime 実行時間
   * @param httpStatusCode Httpステータスコード
   */
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

  /**
   * 書式のチェック
   *
   * @param logDataLineNumber ログファイル行数
   * @param url URL
   * @param httpMethod Httpメソッド
   * @param httpStatusCode Httpステータスコード
   * @param excutionTime 実行時間
   * @param aggregationDate 集計日
   * @return　書式が正しい場合 true 正しくない場合 false
   */
  private boolean checkLogPattern(
      long logDataLineNumber,
      String url,
      String httpMethod,
      String httpStatusCode,
      String excutionTime,
      String aggregationDate) {
    if (!urlPattern.matcher(url).find()) {
      logger.warn("{}行目のURL:{}は不正な書式です。", logDataLineNumber, url);
      return false;
    }
    if (!httpMethodPattern.matcher(httpMethod).find()) {
      logger.warn("{}行目のHTTPメソッド:{}は不正な書式です。", logDataLineNumber, httpMethod);
      return false;
    }
    if (!httpStatusCodePattern.matcher(httpStatusCode).find()) {
      logger.warn("{}行目のHTTPステータスコード:{}は不正な書式です。", logDataLineNumber, httpStatusCode);
      return false;
    }
    if (!accessTimesPattern.matcher(excutionTime).find()) {
      logger.warn("{}行目のAPI実行時間:{}は不正な書式です。", logDataLineNumber, excutionTime);
      return false;
    }
    if (!aggregationDatePattern.matcher(aggregationDate).find()) {
      logger.warn("{}行目の集計日:{}は不正な書式です。", logDataLineNumber, aggregationDate);
      return false;
    }
    return true;
  }

  /**
   * リストをDBに保存
   *
   * @param apiAccessLogDtoList
   */
  private void saveAllLogs(List<LogDto> apiAccessLogDtoList) {
    List<Log> apiAccessLogEntityList =
        apiAccessLogDtoList.stream().map(this::convertToEntity).collect(Collectors.toList());
    logRepository.saveAll(apiAccessLogEntityList);
  }

  private Log convertToEntity(LogDto logDto) {
    return new Log(logDto);
  }

  /**
   * 開始日から終了日の集計日を検索
   *
   * @param startDate 開始日
   * @param endDate　終了日
   * @return 開始日から集計日までの間の集計日
   */
  public List<Log> searchAggregate(LocalDate startDate, LocalDate endDate) {
    return logRepository.findByAggregateDateBetween(startDate, endDate);
  }

  /** API名の定数設定 */
  enum ApiName {
    OTHERS("", ""),
    商品登録API("^/api/products/?", HttpMethod.POST.name()),
    商品取得API("^/api/products/[0-9]+$", HttpMethod.GET.name()),
    商品変更API("^/api/products/[0-9]+$", HttpMethod.PUT.name()),
    商品削除API("^/api/products/[0-9]+$", HttpMethod.DELETE.name()),
    商品複数件取得API("^/api/products/?", HttpMethod.GET.name()),
    商品画像更新API("^/api/products/[0-9]+/images/$", HttpMethod.PATCH.name()),
    商品画像取得API(
        "^/api/products/[0-9]+/images/[A-Za-z0-9-]+(\\.jpeg|\\.jpg|\\.png|\\.gif)$",
        HttpMethod.GET.name());

    private final String requestUrl;
    private final String httpMethod;

    ApiName(String requestUrl, String httpMethod) {
      this.requestUrl = requestUrl;
      this.httpMethod = httpMethod;
    }

    public String getRequestUrl() {
      return requestUrl;
    }

    public String getHttpMethod() {
      return httpMethod;
    }

    /**
     * リクエストURL、HTTPメソッドにマッチするAPI名を返却
     *
     * @param requestUrl リクエストURL
     * @param httpMethod HTTPメソッド
     * @return マッチした名前
     */
    public static ApiName getApiName(String requestUrl, String httpMethod) {

      return Arrays.stream(ApiName.values())
          .filter(
              apiName ->
                  requestUrl.matches(apiName.getRequestUrl())
                      && apiName.getHttpMethod().equals(httpMethod))
          .findFirst()
          .orElse(ApiName.OTHERS);
    }
  }
}
