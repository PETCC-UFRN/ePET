package br.ufrn.ePET.models;

import javax.persistence.Column;

//import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@Column(columnDefinition = "VARCHAR(100)", unique = true)
	@NotEmpty(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	private String email;
	
	//@Column(columnDefinition = "VARCHAR(20)")
	@NotEmpty(message = "{senha.not.blank}")
	private String senha;
	
	public Long getidUsuario() {
		return idUsuario;
	}
	
	public void setidUsuario(Long id) {
		this.idUsuario = id;
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
