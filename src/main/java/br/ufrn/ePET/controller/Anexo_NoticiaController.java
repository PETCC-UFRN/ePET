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
import org.springframework.transaction.annotation.Transactional;
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
	@ApiOperation(value = "Método que retorna os anexos de determinada notícia através de seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getAnexos(@ApiParam(value = "Id da notpicia que se solicita os anexos") @PathVariable Long id){
		List<Anexo_Noticia> lista = anexo_NoticiaService.buscarPorNoticia(id);
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum anexo cadastrado.");
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-noticia-cadastro/{id_noticia}")
	@ApiOperation(value = "Método que salva os anexos de determinada notícia através de seu ID.")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveAnexos(@ApiParam(value = "Id da notícia que cria o anexos") @PathVariable Long id_noticia, 
										@ApiParam(value = "Anexos") @Valid @RequestBody Anexo_Noticia anexo_Noticia){
		anexo_NoticiaService.salvar(id_noticia, anexo_Noticia);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/anexos-noticia-upload/{id_noticia}")
	@ApiOperation(value = "Método que faz o upload dos arquivos.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> uploadFile(@ApiParam(value = "arquivo a ser anexado") @RequestParam("file") MultipartFile file, 
										@ApiParam(value = "id da notícia") @PathVariable Long id_noticia) {
	     String filename = fileStorageService.storeFile(file);
	     System.out.println(filename);
	     if(filename != null) {
	    	 Anexo_Noticia anexo_Noticia = new Anexo_Noticia();
	    	 anexo_Noticia.setAnexos(filename);
	    	 //anexo_NoticiaService.salvar(id_noticia, anexo_Noticia);
	    	 return new ResponseEntity<>(anexo_NoticiaService.salvar(id_noticia, anexo_Noticia), HttpStatus.OK);
	     } else {
	    	 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	}
	
	
	@DeleteMapping(value = "/anexos-noticia-remove/{id}")
	@ApiOperation(value = "Método que remove os anexos de uma notícia.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	@Transactional
	public ResponseEntity<?> removeAnexos(@ApiParam(value = "id do anexo a ser removido") @PathVariable Long id){
		anexo_NoticiaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
