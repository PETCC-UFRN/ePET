package br.ufrn.ePET.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Participante;
import br.ufrn.ePET.service.Anexo_ParticipanteService;
import br.ufrn.ePET.service.FileStorageService;

public class Anexo_ParticipanteController {
	
	private final Anexo_ParticipanteService anexo_ParticipanteService;
	private final FileStorageService fileStorageService;
	
	@Autowired
	public Anexo_ParticipanteController(Anexo_ParticipanteService anexo_ParticipanteService,
			FileStorageService fileStorageService) {
		this.anexo_ParticipanteService = anexo_ParticipanteService;
		this.fileStorageService = fileStorageService;
	}
	
	@GetMapping(value = "/anexos-participantes/{id}")
	public ResponseEntity<?> getAnexos(@PathVariable Long id){
		List<Anexo_Participante> lista = anexo_ParticipanteService.buscarPorParticipante(id);
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum anexo cadastrado.");
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-participante-cadastro/{id_noticia}")
	public ResponseEntity<?> saveAnexos(@PathVariable Long id_participante, @Valid @RequestBody Anexo_Participante anexo_Participante){
		anexo_ParticipanteService.salvar(id_participante, anexo_Participante);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-participante-upload/{id_noticia}")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id_participante) {
	     String filename = fileStorageService.storeFile(file);
	     if(filename != null) {
	    	 Anexo_Participante anexo_Participante = new Anexo_Participante();
	    	 anexo_Participante.setAnexos(filename);
	    	 anexo_ParticipanteService.salvar(id_participante, anexo_Participante);
	    	 return new ResponseEntity<>(HttpStatus.OK);
	     } else {
	    	 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	}
	
	
	@DeleteMapping(value = "/anexos-participante-remove/{id}")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeAnexos(@PathVariable Long id){
		anexo_ParticipanteService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}