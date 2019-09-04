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
	public List<Pessoa> getPessoas(){
		return pessoaservice.buscar();
	}
	
	@GetMapping(value="/pessoas/{id}")
	public Pessoa getPessoas(@PathVariable Long id){
		//return new ResponseEntity<>(pessoaservice.buscar(id), HttpStatus.OK);
		return pessoaservice.buscar(id);
	}
	
	@PostMapping(value="/pessoas-cadastro/{id}")
	public ResponseEntity<?> savePessoas(@PathVariable Long id, @RequestBody Pessoa pessoa){
		pessoaservice.salvar(id, pessoa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
