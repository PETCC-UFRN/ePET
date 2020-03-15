package br.ufrn.ePET.controller;

import br.ufrn.ePET.service.ValidadorUsuarioService;
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
    public ResponseEntity<?> validador(@RequestParam String code){
        validadorUsuarioService.validar(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
