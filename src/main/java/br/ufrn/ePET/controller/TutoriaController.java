package br.ufrn.ePET.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@ApiOperation(value ="Método responsável por retornar as tutorias ativas." )
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	//@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutorias(Pageable pageable){
		Page<Tutoria> tutorias = tutoriaService.buscar(pageable);
		/*if(tutorias.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma tutoria cadastrada");
		}*/
		
		return new ResponseEntity<>(tutorias, HttpStatus.OK);
	}
	
	@GetMapping(value="/tutorias/{id}")
	@ApiOperation(value="Método responsábel por buscar uma determinada tutoria, passada por seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	//@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutoria(@ApiParam(value="Id da tutoria a ser solicitada.") @PathVariable Long id){
		Tutoria tutoria = tutoriaService.buscar(id);
		if(tutoria == null) {
			throw new ResourceNotFoundException("Tutoria com o id: " + id + " não foi encontrada.");
		}
		
		return new ResponseEntity<>(tutoria, HttpStatus.OK);
	}

	@GetMapping(value = "/pesquisar-petiano-tutoria/{search}")
	@ApiOperation(value="Método responsável por buscar as tutorias de algum petiado via CPF ou Nome.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriaPetiano(@ApiParam(value="Nome ou CPF do petiano de interesse.") @PathVariable String search,
											   Pageable pageable){
		Page<Tutoria> tutorias = tutoriaService.buscarPorNomeOuCpfPetiano(search, pageable);
		if(tutorias.isEmpty()){
			throw new ResourceNotFoundException("Nenhuma tutoria encontrada");
		} else {
			return new ResponseEntity<>(tutorias, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/pesquisar-disciplina-tutoria/{search}")
	@ApiOperation(value="Método responsábel por buscar as tutorias de uma disciplina pelo Nome ou Código.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriaDisciplina(@ApiParam(value="Nome ou código da disciplina a ser solicitada") @PathVariable String search, 
												  Pageable pageable){
		Page<Tutoria> tutorias = tutoriaService.buscarPorNomeOuCodigoDisciplina(search, pageable);
		if(tutorias.isEmpty()){
			throw new ResourceNotFoundException("Nenhuma tutoria encontrada");
		} else {
			return new ResponseEntity<>(tutorias, HttpStatus.OK);
		}
	}


	@PostMapping(value="/tutoria-cadastro/{id_petiano}/{id_disciplina}")
	@ApiOperation(value="Método responsável por criar uma nova tutoria.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveTutoria(@ApiParam(name = "id_petiano", value= "ID do petiano que está cadastrando a tutoria." ) @PathVariable Long id_petiano,
										 @ApiParam(name="id_disciplina", value="ID da disciplina que o petiano está cadastrando uma tutoria.") @PathVariable Long id_disciplina){
		Tutoria tutoria = new Tutoria();
		tutoria.setAtivo(true);
		tutoriaService.salvar(id_petiano, id_disciplina, tutoria);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/tutoria-desativa/{id}")
	@ApiOperation(value="Método responsável por desativar(deletar) uma tutoria.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> deleteTutoria(@ApiParam(value="Id da tutoria a ser desativada") @PathVariable Long id){
		tutoriaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
