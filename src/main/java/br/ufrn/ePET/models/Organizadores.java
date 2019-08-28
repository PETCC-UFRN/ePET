package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Organizadores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idOrganizadores;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_petiano")
	private Petiano petiano;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_evento")
	private Evento evento;

	public long getIdOrganizadores() {
		return idOrganizadores;
	}

	public void setIdOrganizadores(long idOrganizadores) {
		this.idOrganizadores = idOrganizadores;
	}

	public Petiano getPetiano() {
		return petiano;
	}

	public void setPetiano(Petiano petiano) {
		this.petiano = petiano;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
}
