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

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Noticia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idNoticia;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty
	@ApiModelProperty(
	  value = "Título da notícia",
	  name = "titulo",
	  dataType = "String",
	  example = "PETCC-UFRN abre processo seletivo.")
	private String titulo;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	@ApiModelProperty(
	  value = "Corpo da notícia",
	  name = "corpo",
	  dataType = "String",
	  example = "O PETCC da UFRN abre processo seletivo interno para novos integrantes.")
	private String corpo;

	@ApiModelProperty(
	  value = "Data de inicio da exibição da notícia",
	  dataType = "Date",
	  example = "10/03/2019")
	private LocalDate inicio_exibicao;
	//@Column(columnDefinition = 	"DATE")
	@ApiModelProperty(
	  value = "Data de fim da exibição da notícia",
	  dataType = "Date",
	  example = "30/03/2019")
	private LocalDate limite_exibicao;

	@Column(columnDefinition = "TEXT")
	private String imagem;
	
	@ManyToOne
	@JoinColumn(name = "id_petiano")
	@ApiModelProperty(
	  value = "Id do petiano que cadastrou a notícia.",
	  name = "petiano",
	  dataType = "String",
	  example = "O PETCC da UFRN abre processo seletivo interno para novos integrantes.")
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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
}
