package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Periodo_Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPeriodo_Evento;
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_evento")
	private Evento evento;
	
	@Column(columnDefinition = "DATE")
	@ApiModelProperty(
	  value = "Um dia do evento, isto é, se o evento possui 3 dias, devem ser criados 3 periodos de evento",
	  dataType = "String",
	  example = "2020-12-30")
	private LocalDate dia;

	public long getIdPeriodo_Evento() {
		return idPeriodo_Evento;
	}

	public void setIdPeriodo_Evento(long idPeriodo_Evento) {
		this.idPeriodo_Evento = idPeriodo_Evento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}
	
	
}
