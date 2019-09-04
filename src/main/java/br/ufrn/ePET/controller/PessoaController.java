package br.ufrn.ePET.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;*/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.PessoaRepository;
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
	//@PreAuthorize("hasAnyRole('ADMIN', 'TUTOR')")
	public ResponseEntity<?> getPessoas(){
		if(pessoaservice.buscar() instanceof Pessoa) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/pessoas/{id}")
	public ResponseEntity<?> getPessoas(@PathVariable Long id){
		if (pessoaservice.buscar(id) instanceof Pessoa) {
			return new ResponseEntity<>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/pessoas-cadastro/{id}")
	public ResponseEntity<?> savePessoas(@PathVariable Long id, @RequestBody Pessoa pessoa){
		if(pessoaservice.salvar(id, pessoa) instanceof Pessoa) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
