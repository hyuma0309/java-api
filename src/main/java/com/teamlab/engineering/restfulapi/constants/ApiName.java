package com.teamlab.engineering.restfulapi.constants;

import org.springframework.http.HttpMethod;

/**
 * API名の定数設定
 *
 * @author asada
 */
public enum ApiName {
  商品登録API("^/api/products/$", HttpMethod.POST.name()),
  商品取得API("^/api/products/[0-9]+$", HttpMethod.GET.name()),
  商品変更API("^/api/products/[0-9]+$", HttpMethod.PUT.name()),
  商品削除API("^/api/products/[0-9]+$", HttpMethod.DELETE.name()),
  商品複数件取得API("^/api/products/$", HttpMethod.GET.name()),
  商品画像更新API("^/api/products/[0-9]+/images$", HttpMethod.PATCH.name()),
  商品画像取得API(
      "^/api/products/[0-9]+/images/[A-Za-z0-9-]+(.jpeg|.jpg|.png|.gif)$", HttpMethod.GET.name());

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
   * @return マッチした場合 API名 マッチしなかった場合 null
   */
  public static ApiName getApiName(String requestUrl, String httpMethod) {
    ApiName[] apiNames = ApiName.values();
    for (ApiName apiName : apiNames) {
      if (requestUrl.matches(apiName.getRequestUrl())
          && apiName.getHttpMethod().equals(httpMethod)) {
        return apiName;
      }
    }
    return null;
  }
}
