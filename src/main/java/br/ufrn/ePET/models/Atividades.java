package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Atividades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtividade;

    @Column(columnDefinition = "TEXT")
	@ApiModelProperty(
	  value = "Título da atividade",
	  dataType = "Text",
	  example = "Seminários de Pesquisa.")
	@NotEmpty
    private String titulo;

	@Column(columnDefinition = "TEXT")
	@ApiModelProperty(
	  value = "Conteudo da atividade",
	  dataType = "Text",
	  example = "Esta atividade consiste na elaboração, apresentação e participação dos Petianos em seminários de pesquisa, oferecidos de forma presencial e/ou remota. Os seminários são ministrados por Petianos deste grupo e, eventualmente, por convidados, abordando temas de pesquisa de interesse ou em desenvolvimento pelos mesmos, seja em função de projetos de iniciação científica, projetos de final de curso ou realizadas no âmbito do PET. Estes seminários buscam disseminar conhecimentos de temas de pesquisa variadas, apresentar avanços de pesquisa obtidos e favorecer o surgimento de sinergias entre iniciativas de pesquisa. Os seminários são planejados para ocorrer quinzenalmente, em média, de forma a permitir a participação de cada Petiano do grupo a cada período letivo.")
	@NotEmpty
	private String conteudo;

	public Long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
