package br.ufrn.ePET.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.*;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.service.EventoService;

@RestController
@RequestMapping(value = "/api")
public class EventoController {
	
	private final EventoService eventoService;
	
	@Autowired
	public EventoController(EventoService eventoService) {
		this.eventoService = eventoService;
	}
	
	@GetMapping(value = "/eventos")
	@ApiOperation(value = "Retorna todos os eventos do sistema.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventos(Pageable pageable){
		//try {
			Page<Evento> page = eventoService.buscar(pageable);
			/*if(page.isEmpty()) {
				throw new ResourceNotFoundException("Nenhum evento cadastrado.");
			}*/
			return new ResponseEntity<>(page, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/eventos-abertos")
	@ApiOperation(value = "Retorna todos os eventos abertos do sistema.")
	public ResponseEntity<?> getEventosAbertos(Pageable pageable){
		//try {
			List<Evento> page = eventoService.buscarAtivos();
			if(page.isEmpty()) {
				throw new ResourceNotFoundException("Nenhum evento aberto para a inscrição.");
			}
			return new ResponseEntity<>(page, HttpStatus.OK);
		//} catch (Exception e) {
			//throw new ResourceNotFoundException("Nenhum evento aberto para a inscrição");
		//}
	}
	
	@GetMapping(value = "/eventos/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que busca um evento a partir de um ID.")
	public ResponseEntity<?> getEventos(@ApiParam(value = "Id do evento procurado") @PathVariable Long id){
		Evento evento = eventoService.buscar(id);
		if(evento == null)
			throw new ResourceNotFoundException("Nenhum evento com id "+id +" encontrado.");
		//try {
			return new ResponseEntity<>(evento, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "eventos-inativos")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Retorna os eventos inativos do sistema.")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventosInativos(){
		return new ResponseEntity<>(eventoService.buscarInativos(), HttpStatus.OK);
	}

	@GetMapping(value = "pesquisar-evento/{search}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que retorna os eventos a partir de um título.")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventoPorTitulo(@ApiParam(value = "Título do evento a ser procurado") @PathVariable String search, Pageable pageable){
		Page<Evento> eventos = eventoService.buscarPorTitulo(search, pageable);
		if(eventos.isEmpty()){
			throw new ResourceNotFoundException("Nenhum evento encontrado");
		} else {
			return new ResponseEntity<>(eventos, HttpStatus.OK);
		}
	}
	@PostMapping(value = "/eventos-ativar/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que ativa um evento a partir de um ID(SOMENTE UM TUTOR REALIZA ESSA AÇÂO).")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> ativarEventos(@ApiParam(value = "Id do avento a ser ativado") @PathVariable Long id){
		//try {
			eventoService.ativar(id);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@PostMapping(value = "/eventos-cadastrar")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Cadastra um novo evento no sistema.")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveEventos(@Valid @RequestBody Evento evento){
		//try {
			eventoService.salvar(evento);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@CrossOrigin
	@DeleteMapping(value="/eventos-remove/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Remove um evento do sistema a partir de seu ID.")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeEventos(@ApiParam(value = "Id do evento a ser removido") @PathVariable Long id){
		//try {
			eventoService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

}
