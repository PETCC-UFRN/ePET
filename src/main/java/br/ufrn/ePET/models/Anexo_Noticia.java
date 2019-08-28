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
public class Anexo_Noticia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAnexos_noticias;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	private String anexos;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_noticia")
	private Noticia noticia;

	public long getIdAnexos_noticias() {
		return idAnexos_noticias;
	}

	public void setIdAnexos_noticias(long idAnexos_noticias) {
		this.idAnexos_noticias = idAnexos_noticias;
	}

	public String getAnexos() {
		return anexos;
	}

	public void setAnexos(String anexos) {
		this.anexos = anexos;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
	
}
