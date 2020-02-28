package br.ufrn.ePET.controller;

import br.ufrn.ePET.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/sign-in/")
    public String login(@RequestParam String email, @RequestParam String senha){
        return usuarioService.signin(email, senha);
    }

}
