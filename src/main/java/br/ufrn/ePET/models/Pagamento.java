package br.ufrn.ePET.models;

import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPagamento;
	
	@ManyToOne
	@JoinColumn(name = "id_participante")
	private Participante participante;

	@Column(columnDefinition = "DATE")
	private Date data_criacao;

	@Column(columnDefinition = "DATE")
	private Date data_atualizacao;

	private String status;
	
	private String referencia_pagseguro;
	
	private String id_transacao_pagseguro;

	public int getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public Date getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(Date data_criacao) {
		this.data_criacao = data_criacao;
	}

	public Date getData_atualizacao() {
		return data_atualizacao;
	}

	public void setData_atualizacao(Date data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReferencia_pagseguro() {
		return referencia_pagseguro;
	}

	public void setReferencia_pagseguro(String referencia_pagseguro) {
		this.referencia_pagseguro = referencia_pagseguro;
	}

	public String getId_transacao_pagseguro() {
		return id_transacao_pagseguro;
	}

	public void setId_transacao_pagseguro(String id_transacao_pagseguro) {
		this.id_transacao_pagseguro = id_transacao_pagseguro;
	}
}
