package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Periodo_Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPeriodo_Evento;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_evento")
	private Evento evento;
	
	@Column(columnDefinition = "DATE")
	private LocalDate dia;
}
