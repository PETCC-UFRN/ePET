package br.ufrn.ePET.models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;

import br.ufrn.ePET.interfaces.TutoriaInterface;

@Entity
public class TutorialPresencial extends Tutoria{
	
	private LocalDate data;
	
	private String local;
	
	private String tema;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate localDate) {
		this.data = localDate;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}
	
	

}
