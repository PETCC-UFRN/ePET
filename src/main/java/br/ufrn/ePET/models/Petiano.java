package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Petiano {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPetiano;
	
	@Column(name="data_ingresso", columnDefinition = "DATE")
	//@NotEmpty
	private LocalDate data_ingresso;
	
	@Column(name="data_egresso", columnDefinition = "DATE")
	private LocalDate data_egresso;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty(message = "{int.not.blank}")
	private String area_interesse;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty(message = "{lat.not.blank}")
	private String lattes;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty(message = "{fot.not.blank}")
	private String foto;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty(message = "{sit.not.blank}")
	private String site_pessoal;
	
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

	public long getIdPetiano() {
		return idPetiano;
	}

	public void setIdPetiano(long idPetiano) {
		this.idPetiano = idPetiano;
	}

	public LocalDate getData_ingresso() {
		return data_ingresso;
	}

	public void setData_ingresso(LocalDate data_ingresso) {
		this.data_ingresso = data_ingresso;
	}

	public LocalDate getData_egresso() {
		return data_egresso;
	}

	public void setData_egresso(LocalDate data_egresso) {
		this.data_egresso = data_egresso;
	}

	public String getArea_interesse() {
		return area_interesse;
	}

	public void setArea_interesse(String area_interesse) {
		this.area_interesse = area_interesse;
	}

	public String getLattes() {
		return lattes;
	}

	public void setLattes(String lattes) {
		this.lattes = lattes;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getSite_pessoal() {
		return site_pessoal;
	}

	public void setSite_pessoal(String site_pessoal) {
		this.site_pessoal = site_pessoal;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
