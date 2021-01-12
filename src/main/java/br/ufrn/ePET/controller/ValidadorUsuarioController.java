package br.ufrn.ePET.controller;

import br.ufrn.ePET.models.ResetDTO;
import br.ufrn.ePET.service.ValidadorUsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value="/api")
public class ValidadorUsuarioController {

    private ValidadorUsuarioService validadorUsuarioService;

    public ValidadorUsuarioController(ValidadorUsuarioService validadorUsuarioService) {
        this.validadorUsuarioService = validadorUsuarioService;
    }

    @GetMapping(value = "/validation/")
    @ApiOperation(value = "Método que valida a conta de um usuário.")
    public RedirectView validador(@ApiParam(value = "Código a ser validádo") @RequestParam String code){
        validadorUsuarioService.validar(code);
        RedirectView rv = new RedirectView();
        rv.setUrl("http://petcc.dimap.ufrn.br/email/confirmacao-cadastro");
        return rv;
    }

    @PostMapping(value = "/reset/")
    @ApiOperation(value = "Método que reseta a senha do usuário.")
    public RedirectView validadorSenha(@RequestBody ResetDTO resetDTO){
        validadorUsuarioService.validarSenha(resetDTO);
        RedirectView rv = new RedirectView();
        rv.setUrl("http://petcc.dimap.ufrn.br/email/confirmacao-reset");
        return rv;
    }
}
