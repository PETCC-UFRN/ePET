package br.ufrn.ePET.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Usuario;
import br.ufrn.ePET.service.UsuarioService;

@RestController
@RequestMapping(value="/api")
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	@Autowired
	public UsuarioController(UsuarioService user) {
		this.usuarioService = user;
	}
	
	@GetMapping(value="/usuarios/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getUsuarios(@PathVariable Long id){
		try {
			return new ResponseEntity<>(usuarioService.buscar(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/usuarios-cadastrar")
	public ResponseEntity<?> saveUsuarios(@RequestBody Usuario usuario){
		try {
			usuarioService.salvar(usuario);
			return new ResponseEntity<>( HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

}
