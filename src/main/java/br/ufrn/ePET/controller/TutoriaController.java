package br.ufrn.ePET.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Tutoria;
import br.ufrn.ePET.service.TutoriaService;

@RestController
@RequestMapping(value="/api")
public class TutoriaController {
	
	private final TutoriaService tutoriaService;
	
	@Autowired
	public TutoriaController(TutoriaService tutoriaService) {
		this.tutoriaService = tutoriaService;
	}
	
	@GetMapping(value="/tutorias")
	//@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutorias(Pageable pageable){
		Page<Tutoria> tutorias = tutoriaService.buscar(pageable);
		if(tutorias.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma tutoria cadastrada");
		}
		
		return new ResponseEntity<>(tutorias, HttpStatus.OK);
	}
	
	@GetMapping(value="/tutorias/{id}")
	//@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutoria(@PathVariable Long id){
		Tutoria tutoria = tutoriaService.buscar(id);
		if(tutoria == null) {
			throw new ResourceNotFoundException("Tutoria com o id: " + id + " não foi encontrada.");
		}
		
		return new ResponseEntity<>(tutoria, HttpStatus.OK);
	}
	
	@PostMapping(value="/tutoria-cadastro/{id_petiano}/{id_disciplina}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveTutoria(@PathVariable Long id_petiano, @PathVariable Long id_disciplina){
		Tutoria tutoria = new Tutoria();
		tutoriaService.salvar(id_petiano, id_disciplina, tutoria);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/tutoria-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> deleteTutoria(@PathVariable Long id){
		tutoriaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
