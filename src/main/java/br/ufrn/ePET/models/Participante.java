package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Participante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idParticipante;
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Online evento;
	
	//@NotEmpty
	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean confirmado;
	
	//@NotEmpty
	@Column (columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean espera;

	public long getIdParticipantes() {
		return idParticipante;
	}

	public void setIdParticipantes(long idParticipantes) {
		this.idParticipante = idParticipantes;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Online evento) {
		this.evento = evento;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public boolean isEspera() {
		return espera;
	}

	public void setEspera(boolean espera) {
		this.espera = espera;
	}
	
	
}
