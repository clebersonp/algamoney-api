package com.algaworks.algamoney.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;
	
	@DeleteMapping("/revoke")
	public void revoke(final HttpServletRequest req, HttpServletResponse resp) {
		
		// Criar um token sem valor e seta-lo no response
		final Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		cookie.setSecure(this.algamoneyApiProperty.getSeguranca().isEnabledHttps());
		cookie.setPath(req.getContextPath() + "/oauth/token");
		
		resp.addCookie(cookie);
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
}
