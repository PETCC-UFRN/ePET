package br.ufrn.ePET.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.PessoaRepository;

@RestController
@RequestMapping(value="/api")
public class PessoaController {
	
	@Autowired
	PessoaRepository pessoarepository;
	
	@GetMapping(value="/pessoas")
	public List<Pessoa> getPessoas(){
		return pessoarepository.findAll();
	}
}
