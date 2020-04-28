package br.ufrn.ePET.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class AuthDTO {

    @NotEmpty(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
	@ApiModelProperty(
	  value = "Email do usuário",
	  name = "email",
	  dataType = "String",
	  example = "petcc@dimap.ufrn.br")
    String email;

    @NotEmpty(message = "{senha.not.blank}")
	@ApiModelProperty(
	  value = "Senha do usuário",
	  name = "senha",
	  dataType = "String")
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
