package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Anexo_Evento extends Anexos{
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_evento")
	private Evento evento;

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
