package br.ufrn.ePET.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Noticia;
import br.ufrn.ePET.service.NoticiaService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value = "/api")
public class NoticiaController {

	private final NoticiaService noticiaService;
	
	@Autowired
	public NoticiaController(NoticiaService noticiaService) {
		this.noticiaService = noticiaService;
	}
	
	
	@GetMapping(value = "/noticia")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> getNoticias(Pageable pageable){
		Page<Noticia> page = noticiaService.buscar(pageable);
		if(page.isEmpty()) {
			throw new ResourceNotFoundException("Não há notícias cadastradas");
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	@GetMapping(value = "/noticia/{id}")
	public ResponseEntity<?> getNoticias(@PathVariable Long id){
		Noticia noticia = noticiaService.buscar(id);
		if(noticia == null) {
			throw new ResourceNotFoundException("Nenhum notícia com o id " + id + " encontrada");
		}
		return new ResponseEntity<>(noticia, HttpStatus.OK);
	}
	
	@PostMapping(value = "/noticia-cadastro/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveNoticias(@PathVariable Long id, @Valid @RequestBody Noticia noticia){
		noticiaService.salvar(id, noticia);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@DeleteMapping(value = "/noticia-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeNoticias(@PathVariable Long id){
		noticiaService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
