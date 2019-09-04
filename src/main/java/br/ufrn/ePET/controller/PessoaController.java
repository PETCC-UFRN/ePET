package br.ufrn.ePET.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.PessoaRepository;

@RestController
@RequestMapping(value="/api")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoarepository;
	
	public PessoaController(PessoaRepository repository) {
		this.pessoarepository = repository;
	}
	
	
	
	@GetMapping("/pessoas")
	public List<Pessoa> getPessoas(){
		return pessoarepository.findAll();
	}
	
	@GetMapping("/pessoas/{id}")
	public Optional<Pessoa> getPessoa(@RequestParam(value="idPessoa") Integer id) {
		// Retornar uma pessoa pertencente a um ID.
		return pessoarepository.findById(id);
	}
	
	@PostMapping("/pessoas/create")
	public Pessoa createPessoa(@RequestBody Pessoa person) {
		// Cria uma nova pessoa
		return pessoarepository.save(person);
	}
	
	@DeleteMapping("/pessoas/{id}")
	public boolean deleteEmployee(@PathVariable Integer id) {
		// Deletar um usuário específico
		pessoarepository.deleteById(id);
		return true;
	}
}
