package br.ufrn.ePET.models;

import javax.persistence.Entity;

@Entity
public class Online extends Evento {

	private String url;
	
	private Double nota;
	
	private Integer avaliacoes;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Integer getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(Integer avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	
}
