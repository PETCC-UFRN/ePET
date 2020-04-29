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
public class Tutoria_Ministrada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTutoria_ministrada;
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_tutoria")
	private Tutoria tutoria;
	
	@Column(columnDefinition = "DATE")
	//@NotEmpty
	@ApiModelProperty(
	  value = "Data que a tutoria será realizada",
	  dataType = "Date",
	  example = "2020-04-01")
	private LocalDate data;
	
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean ativo;
	

	public long getIdTutoria_ministrada() {
		return idTutoria_ministrada;
	}

	public void setIdTutoria_ministrada(long idTutoria_ministrada) {
		this.idTutoria_ministrada = idTutoria_ministrada;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Tutoria getTutoria() {
		return tutoria;
	}

	public void setTutoria(Tutoria tutoria) {
		this.tutoria = tutoria;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public boolean getAtivo() {
		return ativo;
	}
}
