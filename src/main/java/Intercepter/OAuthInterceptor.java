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

  private final HttpSession httpSession;

  public OAuthInterceptor(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object handler) {

    HttpSession Session = httpServletRequest.getSession(false);
    if (Session == null & httpSession.getAttribute("token") != null) {
      throw new UnAuthorizedException("ログイン認証できていません。");
    }
    return true;
  }
}
