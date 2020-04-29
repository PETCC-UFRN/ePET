package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Pessoa {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private long idPessoa;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty(message = "{nome.not.blank}")
	@ApiModelProperty(
	  value = "Nome da pesoa",
	  dataType = "String",
	  example = "Daniel Henrique Ferreira Gomes")
	private String nome;
	
	@CPF(message = "{cpf.not.valid}")
	@NotEmpty(message = "{cpf.not.blank}")
	//@Length(min = 11, max = 11)
	@Column(unique = true)
	@ApiModelProperty(
	  value = "CPF da pessoa",
	  dataType = "String",
	  example = "70200000000")
	private String cpf;
	
	/*@NotEmpty
	@Column(columnDefinition = "VARCHAR(100)", unique = true)
	private String email;
	
	@NotEmpty
	@Column(columnDefinition = "VARCHAR(512)")
	private String senha;*/
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_tipo_usuario")
	private Tipo_Usuario tipo_usuario;
	
	@OneToOne
	//@JsonIgnore
	//@NotEmpty
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

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

	public Tipo_Usuario getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(Tipo_Usuario tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
