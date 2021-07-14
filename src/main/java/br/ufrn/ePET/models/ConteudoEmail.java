package br.ufrn.ePET.models;

public class ConteudoEmail {

    String assunto;
    
    String conteudo;

	public ConteudoEmail(String assunto, String conteudo) {
		this.assunto = assunto;
		this.conteudo = conteudo;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
