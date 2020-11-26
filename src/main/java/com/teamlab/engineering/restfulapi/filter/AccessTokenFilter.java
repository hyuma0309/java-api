package com.teamlab.engineering.restfulapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamlab.engineering.restfulapi.entitiy.AccessToken;
import com.teamlab.engineering.restfulapi.exception.ErrorResponse;
import com.teamlab.engineering.restfulapi.service.AccessTokenService;
import com.teamlab.engineering.restfulapi.setting.AccessTokenSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * 認証チェックFilter
 *
 * @author asada
 */
@Component
public class AccessTokenFilter extends OncePerRequestFilter {
  private final AccessTokenService accessTokenService;
  private final AccessTokenSetting accessTokenSetting;
  private final MessageSource messageSource;

  public AccessTokenFilter(
      AccessTokenService accessTokenService,
      AccessTokenSetting accessTokenSetting,
      MessageSource messageSource) {
    this.accessTokenService = accessTokenService;
    this.accessTokenSetting = accessTokenSetting;
    this.messageSource = messageSource;
  }

  private static final Logger logger = LoggerFactory.getLogger(AccessTokenFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String requestHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

    // Authorizationヘッダが空,または存在しない
    if (StringUtils.isBlank(requestHeaderValue)) {
      setErrorResponse(
          response,
          messageSource.getMessage("error.filter.authorizationHeader.isBlank", null, Locale.JAPAN));
      return;
    }

    // ヘッダがBearerから始まっていない
    if (!requestHeaderValue.startsWith(accessTokenSetting.getAuthorizationHeaderFormat())) {
      setErrorResponse(
          response,
          messageSource.getMessage(
              "error.filter.authorizationHeader.startWith", null, Locale.JAPAN));
      return;
    }

    String token =
        requestHeaderValue.substring(
            accessTokenSetting.getAuthorizationHeaderFormat().length() + 1);

    AccessToken apiAccessToken = accessTokenService.getAccessToken(token);
    try {
      // アクセストークンがDBに存在しない
      if (apiAccessToken == null) {
        setErrorResponse(
            response,
            messageSource.getMessage("error.filter.accessToken.null", null, Locale.JAPAN));
        return;
      }

      // アクセストークンの有効期限が切れている
      if (accessTokenService.isAccessTokenDeadlineEnabled(apiAccessToken)) {
        setErrorResponse(
            response,
            messageSource.getMessage(
                "error.filter.accessToken.expiredValidTime", null, Locale.JAPAN));
        return;
      }
      // アクセストークンの更新日時を現在日時に更新
      accessTokenService.overWriteAccessTokenCurrentTimeUpdate(apiAccessToken);

    } catch (Exception e) {
      response.sendError(response.SC_INTERNAL_SERVER_ERROR);
    }

    // フィルタとしての処理を終了
    filterChain.doFilter(request, response);
  }

  private void setErrorResponse(HttpServletResponse response, String message) throws IOException {
    response.setStatus(response.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    ErrorResponse errorResponse =
        new ErrorResponse(
            messageSource.getMessage("error.filter.authorization.failed", null, Locale.JAPAN),
            message);

    ObjectMapper mapper = new ObjectMapper();
    response.getOutputStream().write(mapper.writeValueAsBytes(errorResponse));
  }
}
