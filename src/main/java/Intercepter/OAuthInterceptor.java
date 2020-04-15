package Intercepter;

import com.teamlab.engineering.restfulapi.exception.UnAuthorizedException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * OAuthのInterceptorクラス
 *
 * @author asada
 */
public class OAuthInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object handler) {

    HttpSession httpSession = httpServletRequest.getSession(false);
    if (httpSession == null || httpSession.getAttribute("token") == null) {
      throw new UnAuthorizedException("ログイン認証できていません。");
    }
    return true;
  }
}
