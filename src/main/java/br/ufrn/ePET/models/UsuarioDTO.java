package br.ufrn.ePET.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public class UsuarioDTO {
	
	@NotEmpty(message = "{nome.not.blank}")
	String nome;
	
	@CPF(message = "{cpf.not.valid}")
	@NotEmpty(message = "{cpf.not.blank}")
	@Length(min = 11, max = 11)
	String cpf;
	
	@NotEmpty(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	String email;
	
	@NotEmpty(message = "{senha.not.blank}")
	String senha;
	
	public UsuarioDTO(String nome, String cpf, String email, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
