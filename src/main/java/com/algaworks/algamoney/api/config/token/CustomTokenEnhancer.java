package com.algaworks.algamoney.api.config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.algaworks.algamoney.api.security.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {

	// Adiciona o nome do usuario ao token jwt
	
	@Override
	public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
		final UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		final Map<String, Object> additionalInformation = new HashMap<>();
		additionalInformation.put("nome", usuarioSistema.getUsuario().getNome());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		
		return accessToken;
	}
}
