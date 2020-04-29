package br.ufrn.ePET.controller;

import java.util.List;

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
import br.ufrn.ePET.models.Organizadores;
import br.ufrn.ePET.service.OrganizadoresService;

@RestController
@RequestMapping(value = "/api")
public class OrganizadoresController {
	
	private final OrganizadoresService organizadoresService;
	
	@Autowired
	public OrganizadoresController(OrganizadoresService organizadoresService) {
		this.organizadoresService = organizadoresService;
	}
	
	
	@GetMapping(value = "/organizadores")
	@ApiOperation(value = "Método que retorna todos os organizadores de eventos do sistema.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getOrganizadores(Pageable pageable){
		Page<Organizadores> organizadores = organizadoresService.buscar(pageable);
		/*if (organizadores.isEmpty())
			throw new ResourceNotFoundException("Nenhum organizador cadastrado.");*/
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/organizadores/{id}")
	@ApiOperation(value = "Retorna as informações de um organizador a partir de seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getOrganizadores(@ApiParam(value = "Id do organizador a ser solicitado") @PathVariable Long id){
		Organizadores organizadores =  organizadoresService.buscar(id);
		if(organizadores == null)
			throw new ResourceNotFoundException("Nenhum organizador com id "+ id+" encontrado");
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/organizadores-pessoa/{id}")
	@ApiOperation(value = "Método que retorna todos os eventos e uma determinada pessoa está organizando.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getOrganizadoresPessoa(@ApiParam(value = "Id da pessoa para procurar seus eventos") @PathVariable Long id){
		List<Organizadores> organizadores = organizadoresService.buscarPessoa(id);
		if(organizadores.isEmpty())
			throw new ResourceNotFoundException("Pessoa de id "+ id+ " não organiza nenhum evento");
		
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/organizadores-evento/{id}")
	@ApiOperation(value = "Método que retorna todos os organizadores de um evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getOrganizadoresEvento(@ApiParam(value = "Id do evento") @PathVariable Long id){
		List<Organizadores> organizadores = organizadoresService.buscarEvento(id);
		if(organizadores.isEmpty())
			throw new ResourceNotFoundException("Evento de id "+ id+ " não possui nenhum organizador");
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@GetMapping(value = "/pesquisar-pessoa-organizadores/{search}")
	@ApiOperation(value = "Método que busca no sistema os eventos que uma pessoa está organizando a partir de seu nome ou CPF")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getOrganizadoresPorPessoa(@ApiParam(value = "Nome ou CPF da pessoa.") @PathVariable String search, Pageable pageable){
		Page<Organizadores> organizadores = organizadoresService.buscarPorNomeOuCpfPessoa(search, pageable);
		if(organizadores.isEmpty()){
			throw new ResourceNotFoundException("Nenhuma pessoa encontrada");
		} else {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/pesquisar-evento-organizadores/{search}")
	@ApiOperation(value = "Método que retorna os organizadores de um evento a partir de seu título.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getOrganizadoresPorEvento(@ApiParam(value = "título do evento") @PathVariable String search, Pageable pageable){
		Page<Organizadores> organizadores = organizadoresService.buscarPorTItuloEvento(search, pageable);
		if(organizadores.isEmpty()){
			throw new ResourceNotFoundException("Nenhum evento encontrada");
		} else {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/organizadores-cadastrar/{id_evento}/{id_pessoa}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> salvarOrganizadores(@PathVariable Long id_evento, @PathVariable Long id_pessoa){
		//try {
			organizadoresService.salvar(id_evento, id_pessoa);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//System.out.println(e);
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@DeleteMapping(value = "/organizadores-remove/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removerOrganizadores(@PathVariable Long id){
		//try {
			organizadoresService.remover(id);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
}
