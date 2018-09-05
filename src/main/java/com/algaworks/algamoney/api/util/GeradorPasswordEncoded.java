package com.algaworks.algamoney.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorPasswordEncoded {

	private GeradorPasswordEncoded() {
		super();
	}
	
	public static String encodeToBcrypt(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
}
