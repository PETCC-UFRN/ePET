package br.ufrn.ePET.controller;

import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Noticia;
import br.ufrn.ePET.service.NoticiaService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value = "/api")
public class NoticiaController {

	private final NoticiaService noticiaService;
	
	@Autowired
	public NoticiaController(NoticiaService noticiaService) {
		this.noticiaService = noticiaService;
	}
	
	
	@GetMapping(value = "/noticia")
	@ApiOperation(value = "Método responsável por retornar todas as notícias do sistema.")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> getNoticias(Pageable pageable){
		Page<Noticia> page = noticiaService.buscar(pageable);
		/*if(page.isEmpty()) {
			throw new ResourceNotFoundException("Não há notícias cadastradas");
		}*/
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@GetMapping(value = "/noticias-atuais")
	@ApiOperation(value = "Método responsável por retornar todas as notícias que estão dentro do limite de exibição.")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> getNoticiasAtuais(Pageable pageable){
		Page<Noticia> page = noticiaService.buscarAtuais(pageable);
		if(page == null || page.isEmpty()) {
			throw new ResourceNotFoundException("Não há notícias cadastradas");
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@GetMapping(value = "/noticias-antigas")
	@ApiOperation(value = "Método responsável por retornar todas as notícias que estão dentro do limite de exibição.")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> getNoticiasAntigas(Pageable pageable){
		Page<Noticia> page = noticiaService.buscarAntigas(pageable);
		if(page == null || page.isEmpty()) {
			throw new ResourceNotFoundException("Não há notícias cadastradas");
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	

	@GetMapping(value = "/pesquisar-noticia/{search}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que retorna as noticias a partir de um título.")
	public ResponseEntity<?> getNoticiasPorTitulo(@ApiParam(value = "Título da notícia a ser procurada") @PathVariable String search, Pageable pageable){
		Page<Noticia> noticias = noticiaService.buscarPorTitulo(search, pageable);
		return new ResponseEntity<>(noticias, HttpStatus.OK);
	}
	
	@GetMapping(value = "/noticia/{id}")
	@ApiOperation(value = "Método responsável por buscar uma notícia com base no ID.")
	public ResponseEntity<?> getNoticias(@ApiParam(value = "Id da notícia a ser solicitada") @PathVariable Long id){
		Noticia noticia = noticiaService.buscar(id);
		if(noticia == null) {
			throw new ResourceNotFoundException("Nenhum notícia com o id " + id + " encontrada");
		}
		return new ResponseEntity<>(noticia, HttpStatus.OK);
	}
	
	@PostMapping(value = "/noticia-cadastro/{id}")
	@ApiOperation(value = "Método responsável cadastrar uma nova notícia.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveNoticias(@ApiParam(value = "Id do petiano que está cadastrando a notícia") @PathVariable Long id, 
										  @ApiParam(value = "Notícia a ser cadastrada") @Valid @RequestBody Noticia noticia){
		noticiaService.salvar(id, noticia);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@DeleteMapping(value = "/noticia-remove/{id}")
	@ApiOperation(value = "Método responsável por remover uma notícia com base no ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeNoticias(@ApiParam(value = "Id da notícia a ser removida") @PathVariable Long id){
		noticiaService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
