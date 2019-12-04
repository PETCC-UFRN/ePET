package br.ufrn.ePET.models;

import br.ufrn.ePET.interfaces.EventoInterface;

public abstract class Online implements EventoInterface {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
