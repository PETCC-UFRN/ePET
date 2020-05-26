package br.ufrn.ePET.models;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmailDTO {

    @NotEmpty(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    @ApiModelProperty(
            value = "Email do usuário",
            name = "email",
            dataType = "String",
            example = "petcc@dimap.ufrn.br")
    String email;

    @NotNull
    Long id;

    public EmailDTO(@NotEmpty(message = "{email.not.blank}") @Email(message = "{email.not.valid}") String email, @NotNull Long id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
