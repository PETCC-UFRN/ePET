package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Anexo_Tutoria  extends Anexos{
	
	@ManyToOne
	@JoinColumn(name="id_tutoria")
	private Tutoria tutoria;

	public Tutoria getTutoria() {
		return tutoria;
	}

	public void setTutoria(Tutoria tutoria) {
		this.tutoria = tutoria;
	}
	
	
	
}
