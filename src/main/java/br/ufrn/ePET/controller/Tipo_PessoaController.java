package br.ufrn.ePET.controller;

import java.util.List;
import java.util.Map;

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
import br.ufrn.ePET.models.Tipo_Usuario;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;

@RestController
@RequestMapping(value="/api")
public class Tipo_PessoaController {
	
	@Autowired
	private Tipo_UsuarioRepository type_user_repository;
	
	
	@GetMapping("/tipo-usuario")
	public List<Tipo_Usuario> findAll(){
		return type_user_repository.findAll();
	}
	
	@GetMapping("/tipo-usuario/{id}")
	public Tipo_Usuario findById(@RequestBody Integer id) {		
		return type_user_repository.findById(id);
	}
	
	@PostMapping("/tipo-usuario")
	public Tipo_Usuario criar(Tipo_Usuario tipo) {
		return type_user_repository.save(new Tipo_Usuario(tipo.getIdTipo_usuario(), tipo.getNome()));
	}
	
}
