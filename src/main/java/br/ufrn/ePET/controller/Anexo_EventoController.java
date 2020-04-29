package br.ufrn.ePET.controller;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@ApiOperation(value = "Método que retorna os anexos de determinado evento através de seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getAnexos(@ApiParam(value = "Id do evento") @PathVariable Long id){
		List<Anexo_Evento> lista = anexo_EventoService.buscarPorEvento(id);
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum anexo cadastrado.");
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-evento-cadastro/{id_evento}")
	@ApiOperation(value = "Método que salva os anexos de determinado evento através de seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveAnexos(@ApiParam(value = "Id do evento que possui os anexos") @PathVariable Long id_evento,
										@ApiParam(value = "Anexos") @Valid @RequestBody Anexo_Evento anexo_Evento){
		anexo_EventoService.salvar(id_evento, anexo_Evento);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-evento-upload/{id_evento}")
	@ApiOperation(value = "Método que faz o upload dos arquivos.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> uploadFile(@ApiParam(value = "arquivo a ser anexado") @RequestParam("file") MultipartFile file, 
										@ApiParam(value = "id do participante") @PathVariable Long id_evento) {
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
	@ApiOperation(value = "Método que remove os anexos de um evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeAnexos(@ApiParam(value = "id do anexo a ser removido") @PathVariable Long id){
		anexo_EventoService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
