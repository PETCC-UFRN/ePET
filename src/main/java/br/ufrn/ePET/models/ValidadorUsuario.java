package br.ufrn.ePET.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class ValidadorUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValidadorUsuario;

    @ManyToOne
    //@NotEmpty
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(columnDefinition = "TEXT", unique = true)
    private String code;

    public Long getIdValidadorUsuario() {
        return idValidadorUsuario;
    }

    public void setIdValidadorUsuario(Long idValidadorUsuario) {
        this.idValidadorUsuario = idValidadorUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
