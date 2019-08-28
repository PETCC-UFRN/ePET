package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Anexo_Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAnexo_Evento;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	private String anexos;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_evento")
	private Evento evento;

	public long getIdAnexo_Evento() {
		return idAnexo_Evento;
	}

	public void setIdAnexo_Evento(long idAnexo_Evento) {
		this.idAnexo_Evento = idAnexo_Evento;
	}

	public String getAnexos() {
		return anexos;
	}

	public void setAnexos(String anexos) {
		this.anexos = anexos;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
