package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEvento;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty
	private String titulo;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	private String descricao;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	private String local;
	
	@Column(columnDefinition = "DATE")
	//@NotEmpty
	private LocalDate d_inscricao;
	
	@Column(columnDefinition = "DATE")
	private LocalDate d_inscricao_fim;
	
	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean ativo;
	
	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean participante_anexos;

	//@NotEmpty
	private int qtdVagas;
	
	//@NotEmpty
	private int qtdCargaHoraria;
	
	//@NotEmpty
	private int qtdDias;
	
	//@NotEmpty
	private double valor;

	public long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public boolean isParticipante_anexos() {
		return participante_anexos;
	}

	public void setParticipante_anexos(boolean participante_anexos) {
		this.participante_anexos = participante_anexos;
	}

	public int getQtdVagas() {
		return qtdVagas;
	}

	public void setQtdVagas(int qtdVagas) {
		this.qtdVagas = qtdVagas;
	}

	public int getQtdCargaHoraria() {
		return qtdCargaHoraria;
	}

	public void setQtdCargaHoraria(int qtdCargaHoraria) {
		this.qtdCargaHoraria = qtdCargaHoraria;
	}

	public int getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(int qtdDias) {
		this.qtdDias = qtdDias;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getD_inscricao() {
		return d_inscricao;
	}

	public void setD_inscricao(LocalDate d_inscricao) {
		this.d_inscricao = d_inscricao;
	}

	public LocalDate getD_inscricao_fim() {
		return d_inscricao_fim;
	}

	public void setD_inscricao_fim(LocalDate d_inscricao_fim) {
		this.d_inscricao_fim = d_inscricao_fim;
	}
}
