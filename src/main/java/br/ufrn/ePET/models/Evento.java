package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Entity
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEvento;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty(message = "{tit.not.blank}")
	private String titulo;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty(message = "{desc.not.blank}")
	private String descricao;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty(message = "{loc.not.blank}")
	private String local;
	
	@Column(columnDefinition = "DATE")
	//@NotEmpty
	//@DateTimeFormat
	private LocalDate d_inscricao;
	
	@Column(columnDefinition = "DATE")
	private LocalDate d_inscricao_fim;

	@Column(columnDefinition = "DATE")
	private LocalDate inicio_rolagem;

	@Column(columnDefinition = "DATE")
	private LocalDate fim_rolagem;

	@Column(columnDefinition = "INT DEFAULT 5")
	private int dias_compensacao;

	@Column
	private int percentual;

	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean ativo;
	
	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean participante_anexos;

	//@NotEmpty
	@DecimalMin("0.0")
	private int qtdVagas;
	
	//@NotEmpty
	@DecimalMin("0.0")
	private int qtdCargaHoraria;
	
	//@NotEmpty
	@DecimalMin("0.0")
	private int qtdDias;
	
	//@NotEmpty
	@DecimalMin("0.0")
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

	public LocalDate getInicio_rolagem() {
		return inicio_rolagem;
	}

	public void setInicio_rolagem(LocalDate inicio_rolagem) {
		this.inicio_rolagem = inicio_rolagem;
	}

	public LocalDate getFim_rolagem() {
		return fim_rolagem;
	}

	public void setFim_rolagem(LocalDate fim_rolagem) {
		this.fim_rolagem = fim_rolagem;
	}

	public int getPercentual() {
		return percentual;
	}

	public void setPercentual(int percentual) {
		this.percentual = percentual;
	}

	public int getDias_compensacao() {
		return dias_compensacao;
	}

	public void setDias_compensacao(int dias_compensacao) {
		this.dias_compensacao = dias_compensacao;
	}
}
