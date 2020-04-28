package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEvento;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@NotEmpty(message = "{tit.not.blank}")
	@ApiModelProperty(
	  value = "Título do evento",
	  dataType = "String",
	  example = "Minicurso de Latex")
	private String titulo;
	
	@Column(columnDefinition = "TEXT")
	@ApiModelProperty(
	  value = "Descrição detalhada do evento.",
	  dataType = "String",
	  example = "Minicurso de introdução a latex para quem nunca teve contato.")
	@NotEmpty(message = "{desc.not.blank}")
	private String descricao;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty(message = "{loc.not.blank}")
	@ApiModelProperty(
	  value = "Local onde será realizado o evento.",
	  dataType = "String",
	  example = "DIMAP LCC3")
	private String local;
	
	@Column(columnDefinition = "DATE")
	//@NotEmpty
	//@DateTimeFormat
	@ApiModelProperty(
	  value = "Data inicial de inscrição para o evento.",
	  dataType = "Date",
	  example = "30-01-2020")
	private LocalDate d_inscricao;
	
	@Column(columnDefinition = "DATE")
	@ApiModelProperty(
	  value = "Data final para a inscrição",
	  dataType = "Date",
	  example = "28-02-2020")
	private LocalDate d_inscricao_fim;
	
	@Column(columnDefinition = "DATE")
	//@NotEmpty
	//@DateTimeFormat
	@ApiModelProperty(
	  value = "Data inicial do evento.",
	  dataType = "Date",
	  example = "01-03-2020")
	private LocalDate d_evento_inicio;
	
	@Column(columnDefinition = "DATE")
	@ApiModelProperty(
	  value = "Data final para do evento",
	  dataType = "Date",
	  example = "02-03-2020")
	private LocalDate d_evento_fim;

	@ApiModelProperty(
	  value = "Data inicial para a chamada da lista deespera do evento",
	  dataType = "Date",
	  example = "20-02-2020")
	@Column(columnDefinition = "DATE")
	private LocalDate inicio_rolagem;

	@ApiModelProperty(
	  value = "Data final para a chamada da lista de espera do evento",
	  dataType = "Date",
	  example = "28-02-2020")
	@Column(columnDefinition = "DATE")
	private LocalDate fim_rolagem;

	@Column(columnDefinition = "INT DEFAULT 5")
	@ApiModelProperty(
	  value = "Quantidade de dias limite para a compensação do pagamento do evento",
	  dataType = "Integer",
	  example = "5")
	private int dias_compensacao;

	@Column
	@ApiModelProperty(
	  value = "Percentual exigido para a emissão da declaração",
	  dataType = "Integer",
	  example = "75")
	private int percentual;

	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean ativo;
	
	@Column (columnDefinition = "BOOLEAN DEFAULT FALSE")
	@ApiModelProperty(
	  value = "Campo que informa ao sistema se esse evento irá possuir anexos",
	  dataType = "Boolean",
	  example = "false")
	private boolean participante_anexos;

	//@NotEmpty
	@DecimalMin("0.0")
	@ApiModelProperty(
	  value = "Quantidade de pessoas máxima permitida no evento",
	  dataType = "Integer",
	  example = "40")
	private int qtdVagas;
	
	//@NotEmpty
	@DecimalMin("0.0")
	@ApiModelProperty(
	  value = "Carga horária do evento",
	  dataType = "Integer",
	  example = "20")
	private int qtdCargaHoraria;
	
	//@NotEmpty
	@DecimalMin("0.0")
	@ApiModelProperty(
	  value = "Quantidade de dias do evento",
	  dataType = "Integer",
	  example = "2")
	private int qtdDias;
	
	//@NotEmpty
	@DecimalMin("0.0")
	@ApiModelProperty(
	  value = "Valor em reais do preço para participar do evento.",
	  dataType = "Double",
	  example = "20.00")
	private double valor;

	@Column(columnDefinition = "TEXT DEFAULT Declaro, para os devidos fins, que {nome_participante}, portador do CPF {cpf}, participou do evento {titulo_evento}, realizado no {local}," + 
			" nos dias {data_inicio} à {data_fim}, com uma carga-horária total de {carga_horária}h. Este evento foi promovido pelo Programa de " + 
			" Educação Tutorial do Curso de Ciência da Computação da Universidade Federal do Rio Grande do Norte (PET-CC/UFRN)." )
	@ApiModelProperty(
	  value = "Texto padrão para a declaração do evento, variáveis disponíveis: {nome_participante}, {cpf}, {titulo_evento}, {carga_horaria},"
	  		+ " {data_inicio}, {data_fim} .",
	  dataType = "Text",
	  example = "Declaro, para os devidos fins, que {nome_participante}, portador do CPF {cpf}, participou do evento {titulo_evento}, realizado no {local},"
	  		+ " nos dias {data_inicio} à {data_fim}, com uma carga-horária total de {carga_horária}h. Este evento foi promovido pelo Programa de "
	  		+ "Educação Tutorial do Curso de Ciência da Computação da Universidade Federal do Rio Grande do Norte (PET-CC/UFRN).")
	private String textoDeclaçãoEvento;

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

	public LocalDate getD_evento_inicio() {
		return d_evento_inicio;
	}

	public void setD_evento_inicio(LocalDate d_evento_inicio) {
		this.d_evento_inicio = d_evento_inicio;
	}

	public LocalDate getD_evento_fim() {
		return d_evento_fim;
	}

	public void setD_evento_fim(LocalDate d_evento_fim) {
		this.d_evento_fim = d_evento_fim;
	}

	public String getTextoDeclaçãoEvento() {
		return textoDeclaçãoEvento;
	}

	public void setTextoDeclaçãoEvento(String textoDeclaçãoEvento) {
		this.textoDeclaçãoEvento = textoDeclaçãoEvento;
	}

}
