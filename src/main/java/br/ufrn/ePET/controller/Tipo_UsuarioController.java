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

import br.ufrn.ePET.models.Tipo_Usuario;
import br.ufrn.ePET.service.Tipo_UsuarioService;

@RestController
@RequestMapping(value="api")
public class Tipo_UsuarioController {
	
	private final Tipo_UsuarioService tipo_UsuarioService;
	
	@Autowired
	public Tipo_UsuarioController(Tipo_UsuarioService tipo_UsuarioService) {
		this.tipo_UsuarioService = tipo_UsuarioService;
	}

	@GetMapping(value="/tipo-usuario")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> getTiposUsuario(){
		try {
			return new ResponseEntity<>(tipo_UsuarioService.buscar(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value="/tipo-usuario/{id}")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> getTiposUsuario(@PathVariable Long id){
		try {
			return new ResponseEntity<>(tipo_UsuarioService.buscar(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value="/tipo-usuario-cadastro/{id}")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> saveTiposUsuario(@PathVariable Long id, @RequestBody Tipo_Usuario tipo_Usuario){
		
		try{
			tipo_UsuarioService.salvar(id, tipo_Usuario);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
