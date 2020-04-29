package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
public class Anexos{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAnexo;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	@ApiModelProperty(
	  value = "Arquivos",
	  dataType = "String",
	  example = "file.rar")
	private String anexos;

	public long getIdAnexo() {
		return idAnexo;
	}

	public void setIdAnexo(long idAnexo) {
		this.idAnexo = idAnexo;
	}

	public String getAnexos() {
		return anexos;
	}

	public void setAnexos(String anexos) {
		this.anexos = anexos;
	}
	
}
