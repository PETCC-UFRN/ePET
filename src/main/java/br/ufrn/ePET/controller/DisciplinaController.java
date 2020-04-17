package br.ufrn.ePET.controller;

import javax.validation.Valid;

import br.ufrn.ePET.error.ResourceNotFoundException;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping(value = "/disciplinas/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getDisciplinas(@PathVariable Long id){
		try{
			Disciplina d = disciplinaService.buscar(id);
			return new ResponseEntity<>(d, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/disciplinas-ativas")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getDisciplinasAtivas(Pageable pageable){
		try{
			return new ResponseEntity<>(disciplinaService.buscarAtivos(pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value="/disciplinas")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getDisciplinas(Pageable pageable){
		try {
			return new ResponseEntity<>(disciplinaService.buscar(pageable), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "pesquisar-diciplina/{search}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getDiscipllinasPorNomeOuCodigo(@PathVariable String search, Pageable pageable){
		Page<Disciplina> disciplinas = disciplinaService.buscarPorNomeOuCodigo(search, pageable);
		if(disciplinas.isEmpty()){
			throw new ResourceNotFoundException("Nenhuma disciplina encontrada");
		} else {
			return new ResponseEntity<>(disciplinas, HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/disciplinas")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveDisciplina(@Valid @RequestBody Disciplina disciplina){
		try {
			disciplinaService.salvar(disciplina);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*@DeleteMapping(value = "disciplinas-remover/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> deleteDisciplinas(@PathVariable Long id){
		try {
			disciplinaService.remover(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResourceNotFoundException("A disciplina que se tentou deletar não existe!");
		}
	}*/
	
	
}
