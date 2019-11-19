package br.ufrn.ePET.models;

import java.util.List;

import javax.persistence.Entity;

import br.ufrn.ePET.interfaces.EventoInterface;


public abstract class EventoPresencial implements EventoInterface{
	
	private List<String> Locais;

	public List<String> getLocais() {
		return Locais;
	}

	public void setLocais(List<String> locais) {
		Locais = locais;
	}
	
}
