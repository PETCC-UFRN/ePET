package br.ufrn.ePET.models;

import java.time.LocalDate;

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
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Evento evento;
	
	@Column(columnDefinition = "DATE")
	private LocalDate data;
	
	private String status;
	
	private String referencia_pagseguro;
	
	private String id_transacao_pagseguro;

	public int getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
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
