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

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.service.PessoaService;

@RestController
@RequestMapping(value="/api")
public class PessoaController {
	
	private final PessoaService pessoaservice;
	
	@Autowired
	public PessoaController(PessoaService pessoaservice) {
		this.pessoaservice = pessoaservice;
	}
	
	
	@GetMapping(value="/pessoas")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getPessoas(){
		try {
			return new ResponseEntity<>(pessoaservice.buscar(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value="/pessoas/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> getPessoas(@PathVariable Long id){
		try {
			return new ResponseEntity<>(pessoaservice.buscar(id), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/pessoas-cadastro/{id_tipo}/{id_usuario}")
	//@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> savePessoas(@PathVariable Long id_tipo, @PathVariable Long id_usuario,@RequestBody Pessoa pessoa){
		try {
			pessoaservice.salvar(id_tipo, id_usuario,pessoa);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
