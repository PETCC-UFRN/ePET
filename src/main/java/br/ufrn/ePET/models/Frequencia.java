package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Frequencia {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFrequencia;
	
	@NotEmpty
	private int assiduidade;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_periodo_evento")
	private Periodo_Evento periodo_evento;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_participante")
	private Participante participante;

	public long getIdFrequencia() {
		return idFrequencia;
	}

	public void setIdFrequencia(long idFrequencia) {
		this.idFrequencia = idFrequencia;
	}

	public int getAssiduidade() {
		return assiduidade;
	}

	public void setAssiduidade(int assiduidade) {
		this.assiduidade = assiduidade;
	}

	public Periodo_Evento getPeriodo_evento() {
		return periodo_evento;
	}

	public void setPeriodo_evento(Periodo_Evento periodo_evento) {
		this.periodo_evento = periodo_evento;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
}
