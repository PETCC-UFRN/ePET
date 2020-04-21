package br.ufrn.ePET.models;

import javax.persistence.*;

@Entity
public class Informacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInformacao;

    @Column(columnDefinition = "TEXT")
    private String sobre;

    private String telefone;

    private String endereco;

    private String email;

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
}
