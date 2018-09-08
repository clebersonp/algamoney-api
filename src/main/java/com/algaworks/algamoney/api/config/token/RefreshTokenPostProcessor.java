package com.algaworks.algamoney.api.config.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMember().getName().equalsIgnoreCase("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		final HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		final HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		
		final String refreshToken = body.getRefreshToken().getValue();
		this.adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		this.removerRefreshTokenDoBody(body);
		
		return body;
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest request,
			HttpServletResponse response) {
		
		final Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(Boolean.TRUE); // nao permitir que js consiga manipular
		refreshTokenCookie.setSecure(this.algamoneyApiProperty.getSeguranca().isEnabledHttps());
		refreshTokenCookie.setPath(request.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(2_592_000); // 30 dias em segundos de duracao
		
		response.addCookie(refreshTokenCookie);
	}

	private void removerRefreshTokenDoBody(OAuth2AccessToken body) {
		DefaultOAuth2AccessToken oauth2 = (DefaultOAuth2AccessToken) body;
		oauth2.setRefreshToken(null);
	}
}
