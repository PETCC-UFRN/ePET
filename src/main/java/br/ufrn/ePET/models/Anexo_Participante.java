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
public class Anexo_Participante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAnexo_Participante;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	private String anexos;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_participante")
	private Participante participante;
	
}
