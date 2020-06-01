package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

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
	private Evento evento;

	@Column(columnDefinition = "DATE")
	@ApiModelProperty(
	  value = "Data máxima para a confirmação da participação",
	  dataType = "Date",
	  example = "2020-01-01")
	private LocalDate data_maxima;

	//@NotEmpty
	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	@ApiModelProperty(
	  value = "Confirmação da pessoa no evento",
	  dataType = "Boolean",
	  example = "false")
	private boolean confirmado;
	
	//@NotEmpty
	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	@ApiModelProperty(
	  value = "Informa se a pessoa está na lista de espera",
	  dataType = "Boolean",
	  example = "false")
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

	public void setEvento(Evento evento) {
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

	public LocalDate getData_maxima() {
		return data_maxima;
	}

	public void setData_maxima(LocalDate data_maxima) {
		this.data_maxima = data_maxima;
	}
}
