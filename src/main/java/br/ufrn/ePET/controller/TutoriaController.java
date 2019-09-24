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

import java.util.List;

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
	@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutorias(){
		List<Tutoria> tutorias = tutoriaService.buscar();
		if(tutorias.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma tutoria cadastrada");
		}
		
		return new ResponseEntity<>(tutorias, HttpStatus.OK);
	}
	
	@GetMapping(value="/tutorias/:id")
	@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutoria(@PathVariable Long id){
		Tutoria tutoria = tutoriaService.buscar(id);
		if(tutoria == null) {
			throw new ResourceNotFoundException("Tutoria com o id: " + id + " não foi encontrada.");
		}
		
		return new ResponseEntity<>(tutoria, HttpStatus.OK);
	}
	
	@PostMapping(value="/tutoria-cadastro/{id_petiano}/{id_disciplina}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveTutoria(@PathVariable Long id_petiano, @PathVariable Long id_disciplina,
										 @RequestBody Tutoria tutoria){
		tutoriaService.salvar(id_petiano, id_disciplina, tutoria);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}