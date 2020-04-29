package br.ufrn.ePET.controller;

import br.ufrn.ePET.models.ResetDTO;
import br.ufrn.ePET.service.ValidadorUsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
public class ValidadorUsuarioController {

    private ValidadorUsuarioService validadorUsuarioService;

    public ValidadorUsuarioController(ValidadorUsuarioService validadorUsuarioService) {
        this.validadorUsuarioService = validadorUsuarioService;
    }

    @GetMapping(value = "/validation/")
	@ApiOperation(value = "Método que valida a conta de um usuário.")
    public ResponseEntity<?> validador(@ApiParam(value = "Código a ser validádo") @RequestParam String code){
        validadorUsuarioService.validar(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/reset/")
	@ApiOperation(value = "Método que reseta a senha do usuário.")
    public ResponseEntity<?> validadorSenha(@RequestBody ResetDTO resetDTO){
        validadorUsuarioService.validarSenha(resetDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
