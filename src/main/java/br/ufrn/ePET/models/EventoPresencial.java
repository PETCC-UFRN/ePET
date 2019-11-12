package br.ufrn.ePET.models;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class EventoPresencial extends Evento{
	
	private List<String> Locais;
	
	public void realizarFrequencia() {
		
	}

}
