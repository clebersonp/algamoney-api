package com.algaworks.algamoney.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.algaworks.algamoney.api.config.token.CustomTokenEnhancer;

@Profile(value = {"prd"})
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	public AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
						.withClient("angular")
						.secret("@ngul@r0")
						.scopes("read", "write")
						.authorizedGrantTypes("password", "refresh_token")
						.accessTokenValiditySeconds(1800)
						.refreshTokenValiditySeconds(3600 * 24)
					.and()
						.withClient("mobile")
						.secret("m0b1l30")
						.scopes("read")
						.authorizedGrantTypes("password", "refresh_token")
						.accessTokenValiditySeconds(1800)
						.refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(this.accessTokenConverter(), this.tokenEnhancer()));
		
		endpoints
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false) // reusa o tempo de expiracao do refresh_token para novos refres_tokens
			.authenticationManager(this.authenticationManager);
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(this.accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		return accessTokenConverter;
	}
}
