package br.ufrn.ePET.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@ApiOperation(value = "Retorna os tipos de usuários do sistema.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> getTiposUsuario(){
		try {
			return new ResponseEntity<>(tipo_UsuarioService.buscar(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value="/tipo-usuario/{id}")
	@ApiOperation(value = "Retorna o tipo de usuário representante de determinado ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> getTiposUsuario(@ApiParam(value = "Id do tipo de usuário solicitado") @PathVariable Long id){
		try {
			return new ResponseEntity<>(tipo_UsuarioService.buscar(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping(value="/tipo-usuario-cadastro")
	@ApiOperation(value = "Cadastra um novo tipo de usuário.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> saveTiposUsuario(@RequestBody Tipo_Usuario tipo_Usuario){
		
		try{
			tipo_UsuarioService.salvar(tipo_Usuario);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
