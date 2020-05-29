package br.ufrn.ePET.models;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Informacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInformacao;

    @Column(columnDefinition = "TEXT")
	@ApiModelProperty(
	  value = "Texto básico que resume o que é o PETCC-UFRN",
	  dataType = "Text",
	  example = "O Programa de Educação Tutorial é um projeto nacional, organizado através de cursos de graduação das Instituições de Ensino Superior do Brasil. Nosso grupo é do curso de Ciência da Computação (CC) da Universidade Federal do Rio grande do Norte (UFRN).\n" + 
	  		"\n" + 
	  		"Somos orientados por um tutor, atualmente o professor Umberto Costa. Nosso grupo é formado por 12 bolsistas e até 6 voluntários.")
    private String sobre;

	@ApiModelProperty(
	  value = "Telefone do PET",
	  dataType = "String",
	  example = "3030-3030")
    private String telefone;

	@ApiModelProperty(
	  value = "Endereço do PET",
	  dataType = "String",
	  example = "Departamento Informática e Matemática Aplicada - UFRN")
    private String endereco;


	@ApiModelProperty(
	  value = "Email do PET",
	  dataType = "String",
	  example = "petcc@dimap.ufrn.br")
	private String email;
	
	@ApiModelProperty(
	  value = "Email do PET",
	  dataType = "String",
	  example = "youtube.com/petcc-ufrn")
	private String youtube;	

	@ApiModelProperty(
	  value = "Email do PET",
	  dataType = "String",
	  example = "instagram.com/petcc-ufrn")
	private String instagram;

	@ApiModelProperty(
	  value = "Email do PET",
	  dataType = "String",
	  example = "facebook.com/petcc-ufrn")
	private String facebook;	

	@ApiModelProperty(
	  value = "Github do PET",
	  dataType = "String",
	  example = "github.com/petcc-ufrn")
	private String github;

    public Long getIdInformacao() {
        return idInformacao;
    }

    public void setIdInformacao(Long idInformacao) {
        this.idInformacao = idInformacao;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}
}
