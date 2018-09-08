package com.algaworks.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private final Seguranca seguranca = new Seguranca();
	private String originPermitida;
	
	public static class Seguranca {
		
		private boolean enabledHttps;

		public boolean isEnabledHttps() {
			return enabledHttps;
		}
		public void setEnabledHttps(boolean enabledHttps) {
			this.enabledHttps = enabledHttps;
		}
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public String getOriginPermitida() {
		return originPermitida;
	}
	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}
}