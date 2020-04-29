package br.ufrn.ePET.models;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class ResetDTO {

    @NotEmpty
	@ApiModelProperty(
	  value = "Código a ser validado",
	  name = "code",
	  dataType = "String",
	  example = "1231234rerfqwerqwer123r123r123,123434123m4.")
    String code;

    @NotEmpty(message = "{senha.not.blank}")
	@ApiModelProperty(
	  value = "Senha do usuário",
	  dataType = "String",
	  example = "senha123")
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
