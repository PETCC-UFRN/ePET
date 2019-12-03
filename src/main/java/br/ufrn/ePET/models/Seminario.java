package br.ufrn.ePET.models;

import br.ufrn.ePET.interfaces.EventoInterface;

public abstract class Seminario implements EventoInterface {

	private String local;

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	
	
}
