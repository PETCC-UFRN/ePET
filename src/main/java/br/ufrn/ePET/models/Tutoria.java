package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
public abstract class Tutoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTutoria;
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_petiano")
	private Petiano petiano;
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_disciplina")
	private Disciplina disciplina;

	public long getIdTutoria() {
		return idTutoria;
	}

	public void setIdTutoria(long idTutoria) {
		this.idTutoria = idTutoria;
	}

	public Petiano getPetiano() {
		return petiano;
	}

	public void setPetiano(Petiano petiano) {
		this.petiano = petiano;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}
