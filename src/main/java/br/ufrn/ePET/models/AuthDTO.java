package br.ufrn.ePET.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AuthDTO {

    @NotEmpty(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    String email;

    @NotEmpty(message = "{senha.not.blank}")
    String senha;

    public AuthDTO(@NotEmpty(message = "{email.not.blank}") @Email(message = "{email.not.valid}") String email, @NotEmpty(message = "{senha.not.blank}") String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
