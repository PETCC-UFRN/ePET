package br.ufrn.ePET.models;

import java.util.Date;

import br.ufrn.ePET.interfaces.TutoriaInterface;

public abstract class TutorialPresencial implements TutoriaInterface{
	
	private Date data;
	
	private String local;
	
	private String tema;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
