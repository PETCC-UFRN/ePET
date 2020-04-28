package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Petiano {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPetiano;
	
	@Column(name="data_ingresso", columnDefinition = "DATE")
	@ApiModelProperty(
	  value = "Data de ingresso do petiano",
	  name = "data_ingresso",
	  dataType = "Date",
	  example = "2020-12-21.")
	private LocalDate data_ingresso;
	
	@Column(name="data_egresso", columnDefinition = "DATE")
	@ApiModelProperty(
	  value = "Data de egresso do petiano",
	  dataType = "Date",
	  example = "2021-01-30.")
	private LocalDate data_egresso;
	
	@Column(columnDefinition = "TEXT")
	@ApiModelProperty(
	  value = "Areas de interesse do petiano",
	  dataType = "String",
	  example = "Ciencia de dados, aprendizado de máquina, computação numérica.")
	private String area_interesse;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@ApiModelProperty(
	  value = "Link para o lattes do petiano",
	  dataType = "String",
	  example = "latte.com/1234ddddg1yu2")
	private String lattes;
	
	@Column(columnDefinition = "VARCHAR(100)")
	private String foto;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@ApiModelProperty(
	  value = "Link para o site do petiano",
	  dataType = "String",
	  example = "github.com/petcc-ufrn.")
	private String site_pessoal;
	
	@OneToOne
	@JoinColumn(name = "id_pessoa")
	@ApiModelProperty(
	  value = "Id da pessoa a qual se refere o petiano",
	  dataType = "Pessoa")
	private Pessoa pessoa;

	public long getIdPetiano() {
		return idPetiano;
	}

	public void setIdPetiano(long idPetiano) {
		this.idPetiano = idPetiano;
	}

	public LocalDate getData_ingresso() {
		return data_ingresso;
	}

	public void setData_ingresso(LocalDate data_ingresso) {
		this.data_ingresso = data_ingresso;
	}

	public LocalDate getData_egresso() {
		return data_egresso;
	}

	public void setData_egresso(LocalDate data_egresso) {
		this.data_egresso = data_egresso;
	}

	public String getArea_interesse() {
		return area_interesse;
	}

	public void setArea_interesse(String area_interesse) {
		this.area_interesse = area_interesse;
	}

	public String getLattes() {
		return lattes;
	}

	public void setLattes(String lattes) {
		this.lattes = lattes;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getSite_pessoal() {
		return site_pessoal;
	}

	public void setSite_pessoal(String site_pessoal) {
		this.site_pessoal = site_pessoal;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
