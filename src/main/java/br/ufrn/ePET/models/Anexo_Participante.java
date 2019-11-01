package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Anexo_Participante extends Anexos {
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_participante")
	private Participante participante;

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	
}
