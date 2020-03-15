package br.ufrn.ePET.controller;

import br.ufrn.ePET.models.AuthDTO;
import br.ufrn.ePET.models.UsuarioDTO;
import br.ufrn.ePET.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/sign-in/")
    public String login(@Valid @RequestBody AuthDTO authDTO){
        return usuarioService.signin(authDTO.getEmail(), authDTO.getSenha());
    }

    @PostMapping(value="/sign-up/")
    public ResponseEntity<?> saveUsuarios(@Valid @RequestBody UsuarioDTO usuarioDTO){
        //try {
        usuarioService.signup(usuarioDTO);
        return new ResponseEntity<>(HttpStatus.OK);
        //} catch(Exception e) {
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //}
    }
}
