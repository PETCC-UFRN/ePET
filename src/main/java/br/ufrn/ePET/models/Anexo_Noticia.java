package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Anexo_Noticia extends Anexos {

	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_noticia")
	private Noticia noticia;

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
	
}
