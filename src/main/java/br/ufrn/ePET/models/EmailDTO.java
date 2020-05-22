package br.ufrn.ePET.models;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO {

    @NotEmpty(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    @ApiModelProperty(
            value = "Email do usuário",
            name = "email",
            dataType = "String",
            example = "petcc@dimap.ufrn.br")
    String email;

    public EmailDTO(@NotEmpty(message = "{email.not.blank}") @Email(message = "{email.not.valid}") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
