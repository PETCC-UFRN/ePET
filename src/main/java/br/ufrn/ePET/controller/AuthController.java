package br.ufrn.ePET.controller;

import br.ufrn.ePET.models.AuthDTO;
import br.ufrn.ePET.models.UsuarioDTO;
import br.ufrn.ePET.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@ApiOperation(value = "Método que loga um usuário no sistema e retorna um token de autenticação que de ve ser utilizado para as próximas requisições.")
    public String login(@Valid @RequestBody AuthDTO authDTO){
        return usuarioService.signin(authDTO.getEmail(), authDTO.getSenha());
    }

    @PostMapping(value="/sign-up/")
	@ApiOperation(value = "Método que cadastra um novo usuário.")
    public ResponseEntity<?> saveUsuarios(@Valid @RequestBody UsuarioDTO usuarioDTO){
        //try {
        usuarioService.signup(usuarioDTO);
        return new ResponseEntity<>(HttpStatus.OK);
        //} catch(Exception e) {
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //}
    }

    @GetMapping(value = "/forgot")
	@ApiOperation(value = "Método de esquecimento de senha.")
    public ResponseEntity<?> forgotPassword(@ApiParam(value = "email do usuário que esqueceu a senha") @RequestParam String email){
        usuarioService.esqueceuSenha(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
