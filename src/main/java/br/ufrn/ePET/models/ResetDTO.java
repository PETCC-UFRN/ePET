package br.ufrn.ePET.models;

import javax.validation.constraints.NotEmpty;

public class ResetDTO {

    @NotEmpty
    String code;

    @NotEmpty(message = "{senha.not.blank}")
    String senha;

    public ResetDTO(@NotEmpty String code, @NotEmpty(message = "{senha.not.blank}") String senha) {
        this.code = code;
        this.senha = senha;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
