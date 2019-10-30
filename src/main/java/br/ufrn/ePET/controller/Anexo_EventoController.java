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
import br.ufrn.ePET.service.Anexo_EventoService;
import br.ufrn.ePET.service.FileStorageService;

@RestController
@RequestMapping(value = "/api")
public class Anexo_EventoController {
	
	private final Anexo_EventoService anexo_EventoService;
	private final FileStorageService fileStorageService;
	
	@Autowired
	public Anexo_EventoController(Anexo_EventoService anexo_EventoService, FileStorageService fileStorageService) {
		this.anexo_EventoService = anexo_EventoService;
		this.fileStorageService = fileStorageService;
	}
	
	@GetMapping(value = "/anexos-evento/{id}")
	public ResponseEntity<?> getAnexos(@PathVariable Long id){
		List<Anexo_Evento> lista = anexo_EventoService.buscarPorEvento(id);
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum anexo cadastrado.");
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-evento-cadastro/{id_evento}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveAnexos(@PathVariable Long id_evento, @Valid @RequestBody Anexo_Evento anexo_Evento){
		anexo_EventoService.salvar(id_evento, anexo_Evento);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-evento-upload/{id_evento}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id_evento) {
	     String filename = fileStorageService.storeFile(file);
	     if(filename != null) {
	    	 Anexo_Evento anexo_Evento = new Anexo_Evento();
	    	 anexo_Evento.setAnexos(filename);
	    	 anexo_EventoService.salvar(id_evento, anexo_Evento);
	    	 return new ResponseEntity<>(HttpStatus.OK);
	     } else {
	    	 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	}
	
	
	@DeleteMapping(value = "/anexos-evento-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeAnexos(@PathVariable Long id){
		anexo_EventoService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
