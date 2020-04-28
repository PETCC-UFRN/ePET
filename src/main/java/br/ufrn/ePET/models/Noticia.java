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
public class Noticia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idNoticia;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty
	private String titulo;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	private String corpo;
	
	private LocalDate inicio_exibicao;
	//@Column(columnDefinition = 	"DATE")
	private LocalDate limite_exibicao;
	
	@ManyToOne
	@JoinColumn(name = "id_petiano")
	private Petiano petiano;

	public long getIdNoticia() {
		return idNoticia;
	}

	public void setIdNoticia(long idNoticia) {
		this.idNoticia = idNoticia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public LocalDate getLimite_exibicao() {
		return limite_exibicao;
	}

	public void setLimite_exibicao(LocalDate limite_exibicao) {
		this.limite_exibicao = limite_exibicao;
	}

	public Petiano getPetiano() {
		return petiano;
	}

	public void setPetiano(Petiano petiano) {
		this.petiano = petiano;
	}

	public LocalDate getInicio_exibicao() {
		return inicio_exibicao;
	}

	public void setInicio_exibicao(LocalDate inicio_exibicao) {
		this.inicio_exibicao = inicio_exibicao;
	}
	
}
