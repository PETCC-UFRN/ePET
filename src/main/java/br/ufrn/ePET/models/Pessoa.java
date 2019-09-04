package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Pessoa {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private long idPessoa;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty
	private String nome;
	
	@CPF
	@NotEmpty
	@Length(min = 11, max = 11)
	@Column(unique = true)
	private String cpf;
	
	@NotEmpty
	@Column(columnDefinition = "VARCHAR(100)", unique = true)
	private String email;
	
	@NotEmpty
	@Column(columnDefinition = "VARCHAR(512)")
	private String senha;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_tipo_usuario")
	private Tipo_Usuario tipo_usuario;
	
	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
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

	public Tipo_Usuario getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(Tipo_Usuario tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	
}
