package com.teamlab.engineering.restfulapi.batch;

import com.teamlab.engineering.restfulapi.service.LogService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogBatch {
  private final LogService logService;
  private static final Logger logger = LoggerFactory.getLogger(LogBatch.class);

  public LogBatch(LogService logService) {
    this.logService = logService;
  }

  /** APIアクセスログ集計 */
  @Scheduled(initialDelay = 0, fixedDelay = 200000)
  public void apiAccessLogBatch() {
    logger.info("バッチ処理開始");

    try {
      logService.yesterdayAggregateFile();
    } catch (Exception e) {
      logger.error("バッチ処理に失敗しました", e);
    }

    logger.info("バッチ処理終了");
  }
}
