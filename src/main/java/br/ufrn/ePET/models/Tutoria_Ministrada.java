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
	private TutoriaOnline tutoria;
	
	@Column(columnDefinition = "DATE")
	@NotEmpty
	private LocalDate data;
	
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

	public TutoriaOnline getTutoria() {
		return tutoria;
	}

	public void setTutoria(TutoriaOnline tutoria) {
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
