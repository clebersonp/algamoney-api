package com.algaworks.algamoney.api.config.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.ParameterMap;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter {

	private static final String COOKIE_NAME = "refreshToken";
	private static final String PATH_OAUTH_TOKEN = "/oauth/token";
	private static final String GRANT_TYPE = "grant_type";
	private static final String REFRESH_TOKEN = "refresh_token";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;
		
		if (PATH_OAUTH_TOKEN.equalsIgnoreCase(req.getRequestURI())
				&& REFRESH_TOKEN.equalsIgnoreCase(req.getParameter(GRANT_TYPE))
				&& ArrayUtils.isNotEmpty(req.getCookies())) {

			for (final Cookie cookie : req.getCookies()) {
				if (cookie.getName().equalsIgnoreCase(COOKIE_NAME)) {
					final String refreshToken = cookie.getValue();
					req = new MyServletRequestWrapper(req, refreshToken);
				}
			}
		}

		chain.doFilter(req, resp);
	}

	
	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;

		public MyServletRequestWrapper(final HttpServletRequest request, final String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			final ParameterMap<String, String[]> parameterMap = new ParameterMap<>(this.getRequest().getParameterMap());
			parameterMap.put(REFRESH_TOKEN, new String[] { this.refreshToken });
			parameterMap.setLocked(Boolean.TRUE);
			return parameterMap;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
}
