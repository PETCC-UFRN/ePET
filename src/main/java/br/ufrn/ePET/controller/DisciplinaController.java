package br.ufrn.ePET.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Disciplina;
import br.ufrn.ePET.service.DisciplinaService;

@RestController
@RequestMapping(value="/api")
public class DisciplinaController {

	private final DisciplinaService disciplinaService;
	
	@Autowired
	public DisciplinaController(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}
	
	@GetMapping(value="/disciplinas")
	public ResponseEntity<?> getDisciplinas(){
		try {
			return new ResponseEntity<>(disciplinaService.buscar(), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/disciplinas")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveDisciplina(@Valid @RequestBody Disciplina disciplina){
		try {
			disciplinaService.salvar(disciplina);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
