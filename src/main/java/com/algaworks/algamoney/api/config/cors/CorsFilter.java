package com.algaworks.algamoney.api.config.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Origin", this.algamoneyApiProperty.getOriginPermitida());
		
		if (HttpMethod.OPTIONS.name().equalsIgnoreCase(req.getMethod()) 
				&& this.algamoneyApiProperty.getOriginPermitida().equalsIgnoreCase(req.getHeader("Origin"))) {
			
			resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resp.setHeader("Access-Control-Max-Age", "3600");
			
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
