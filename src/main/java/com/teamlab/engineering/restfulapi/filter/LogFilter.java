package com.teamlab.engineering.restfulapi.filter;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/** アクセスログ用のフィルター(商品情報にアクセス時)。 */
@Component
public class LogFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

  public LogFilter() {}

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    // 実行時間計測開始
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    // filterの実行
    filterChain.doFilter(request, response);

    // 計測終了
    stopWatch.stop();

    logger.info(
        "{}\t{}\t{}\t{}\t{}",
        request.getMethod(),
        request.getRequestURI(),
        response.getStatus(),
        stopWatch.getTime(),
        LocalDate.now());
  }
}
