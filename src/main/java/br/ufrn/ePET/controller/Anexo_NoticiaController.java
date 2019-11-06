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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Noticia;
import br.ufrn.ePET.service.Anexo_NoticiaService;
import br.ufrn.ePET.service.FileStorageService;

@RestController
@RequestMapping(value = "/api")
public class Anexo_NoticiaController {
	
	private final Anexo_NoticiaService anexo_NoticiaService;
	private final FileStorageService fileStorageService;
	
	@Autowired
	public Anexo_NoticiaController(Anexo_NoticiaService anexo_NoticiaService, FileStorageService fileStorageService) {
		this.anexo_NoticiaService = anexo_NoticiaService;
		this.fileStorageService = fileStorageService;
	}
	
	@GetMapping(value = "/anexos-noticias/{id}")
	public ResponseEntity<?> getAnexos(@PathVariable Long id){
		List<Anexo_Noticia> lista = anexo_NoticiaService.buscarPorNoticia(id);
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum anexo cadastrado.");
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-noticia-cadastro/{id_noticia}")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveAnexos(@PathVariable Long id_noticia, @Valid @RequestBody Anexo_Noticia anexo_Noticia){
		anexo_NoticiaService.salvar(id_noticia, anexo_Noticia);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-noticia-upload/{id_noticia}")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id_noticia) {
	     String filename = fileStorageService.storeFile(file);
	     if(filename != null) {
	    	 Anexo_Noticia anexo_Noticia = new Anexo_Noticia();
	    	 anexo_Noticia.setAnexos(filename);
	    	 anexo_NoticiaService.salvar(id_noticia, anexo_Noticia);
	    	 return new ResponseEntity<>(HttpStatus.OK);
	     } else {
	    	 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	}
	
	
	@DeleteMapping(value = "/anexos-noticia-remove/{id}")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeAnexos(@PathVariable Long id){
		anexo_NoticiaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
