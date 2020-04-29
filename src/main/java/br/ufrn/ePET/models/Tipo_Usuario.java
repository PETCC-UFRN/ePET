package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Tipo_Usuario{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTipo_usuario;
	
	@Column(columnDefinition = "VARCHAR(45)")
	@NotEmpty
	@ApiModelProperty(
	  value = "Nome do tipo do usuário",
	  name = "nome",
	  dataType = "String",
	  example = "comum")
	private String nome;

	public long getIdTipo_usuario() {
		return idTipo_usuario;
	}

	public void setIdTipo_usuario(long idTipo_usuario) {
		this.idTipo_usuario = idTipo_usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
