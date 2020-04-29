package br.ufrn.ePET.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import io.swagger.annotations.ApiModelProperty;

public class UsuarioDTO {
	
	@NotEmpty(message = "{nome.not.blank}")
	@ApiModelProperty(
	  value = "Nome da pesoa",
	  dataType = "String",
	  example = "Daniel Henrique Ferreira Gomes")
	String nome;
	
	@CPF(message = "{cpf.not.valid}")
	@NotEmpty(message = "{cpf.not.blank}")
	@Length(min = 11, max = 11)
	@ApiModelProperty(
	  value = "CPF da pessoa",
	  dataType = "String",
	  example = "70200000000")
	String cpf;
	
	@NotEmpty(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	@ApiModelProperty(
	  value = "Email do usuário",
	  name = "email",
	  dataType = "String",
	  example = "petcc@dimap.ufrn.br")
	String email;
	
	@NotEmpty(message = "{senha.not.blank}")
	@ApiModelProperty(
	  value = "Senha do usuário",
	  name = "senha",
	  dataType = "String")
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
