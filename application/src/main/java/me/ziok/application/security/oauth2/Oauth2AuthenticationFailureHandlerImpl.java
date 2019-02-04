package me.ziok.application.security.oauth2;

import me.ziok.application.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static me.ziok.application.security.oauth2.HttpCookieOauth2AuthorizationRequestRepositoryImpl.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class Oauth2AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler implements Oauth2AuthenticationFailureHandler {

    @Autowired
    HttpCookieOauth2AuthorizationRequestRepository httpCookieOauth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("/");

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        httpCookieOauth2AuthorizationRequestRepository.removeAuthorizationRequest(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
