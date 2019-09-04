package br.ufrn.ePET.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Tipo_Usuario;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;
import br.ufrn.ePET.service.Tipo_UsuarioService;

@RestController
@RequestMapping(value="api")
public class Tipo_UsuarioController {
	
	private final Tipo_UsuarioService tipo_UsuarioService;
	
	public Tipo_UsuarioController(Tipo_UsuarioService tipo_UsuarioService) {
		this.tipo_UsuarioService = tipo_UsuarioService;
	}

	@GetMapping(value="/tipo-usuario")
	//@PreAuthorize("hasAnyRole('ADMIN', 'TUTOR')")
	public List<Tipo_Usuario> getTiposUsuario(){
		return tipo_UsuarioService.buscar();
	}
	
	@GetMapping(value="/tipo-usuario/{id}")
	public Tipo_Usuario getTiposUsuario(@PathVariable Long id){
		//return new ResponseEntity<>(pessoaservice.buscar(id), HttpStatus.OK);
		return tipo_UsuarioService.buscar(id);
	}
	
	@PostMapping(value="/tipo-usuario-cadastro/{id}")
	public ResponseEntity<?> saveTiposUsuario(@PathVariable Long id, @RequestBody Tipo_Usuario tipo_Usuario){
		tipo_UsuarioService.salvar(id, tipo_Usuario);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}