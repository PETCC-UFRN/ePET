package br.ufrn.ePET.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Evento;
import br.ufrn.ePET.models.Anexo_Tutoria;
import br.ufrn.ePET.service.Anexo_EventoService;
import br.ufrn.ePET.service.Anexo_TutoriaService;
import br.ufrn.ePET.service.FileStorageService;

@RestController
@RequestMapping(value = "/api")
public class Anexo_TutoriaController {

	private final Anexo_TutoriaService anexo_TutoriaService;
	private final FileStorageService fileStorageService;
	
	@Autowired
	public Anexo_TutoriaController(Anexo_TutoriaService anexo_TutoriaService, FileStorageService fileStorageService) {
		this.anexo_TutoriaService = anexo_TutoriaService;
		this.fileStorageService = fileStorageService;
	}
	
	@GetMapping(value = "/anexos-tutoria")
	public ResponseEntity<?> getAnexos(){
		List<Anexo_Tutoria> lista = anexo_TutoriaService.buscarTodos();
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum anexo cadastrado.");
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/anexos-tutoria/{id}")
	public ResponseEntity<?> getAnexos(@PathVariable Long id){
		List<Anexo_Tutoria> lista = anexo_TutoriaService.buscarPortutoria(id);
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum anexo cadastrado.");
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-tutoria-cadastro/{id_tutoria}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveAnexos(@PathVariable Long id_tutoria, @Valid @RequestBody Anexo_Tutoria anexo_Tutoria){
		anexo_TutoriaService.salvar(id_tutoria, anexo_Tutoria);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-tutoria-upload/{id_tutoria}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id_tutoria) {
	     String filename = fileStorageService.storeFile(file);
	     if(filename != null) {
	    	 Anexo_Tutoria anexo_Tutoria = new Anexo_Tutoria();
	    	 anexo_Tutoria.setAnexos(filename);
	    	 anexo_TutoriaService.salvar(id_tutoria, anexo_Tutoria);
	    	 return new ResponseEntity<>(HttpStatus.OK);
	     } else {
	    	 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	}
	
	
	@DeleteMapping(value = "/anexos-tutoria-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeAnexos(@PathVariable Long id){
		anexo_TutoriaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
