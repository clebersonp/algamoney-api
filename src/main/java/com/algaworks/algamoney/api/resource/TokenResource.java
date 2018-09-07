package com.algaworks.algamoney.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

	@DeleteMapping("/revoke")
	public void revoke(final HttpServletRequest req, HttpServletResponse resp) {
		
		// Criar um token sem valor e seta-lo no response
		final Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		cookie.setSecure(false); // TODO Em producao sera true
		cookie.setPath(req.getContextPath() + "/oauth/token");
		
		resp.addCookie(cookie);
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
}
