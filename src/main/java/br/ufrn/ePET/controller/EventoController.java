package br.ufrn.ePET.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.EventoPresencial;
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
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventos(Pageable pageable){
		//try {
			Page<Evento> page = eventoService.buscar(pageable);
			if(page.isEmpty()) {
				throw new ResourceNotFoundException("Nenhum evento cadastrado.");
			}
			return new ResponseEntity<>(page, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/eventos-abertos")
	public ResponseEntity<?> getEventosAbertos(Pageable pageable){
		//try {
			List<Evento> page = eventoService.buscarAtivos(pageable);
			if(page.isEmpty()) {
				throw new ResourceNotFoundException("Nenhum evento cadastrado.");
			}
			return new ResponseEntity<>(page, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/eventos/{id}")
	public ResponseEntity<?> getEventos(@PathVariable Long id){
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
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventosInativos(){
		return new ResponseEntity<>(eventoService.buscarInativos(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/eventos-ativar/{id}")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> ativarEventos(@PathVariable Long id){
		//try {
			eventoService.ativar(id);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@PostMapping(value = "/eventos-cadastrar")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> saveEventos(@Valid @RequestBody Evento evento){
		//try {
			eventoService.salvar(evento);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@DeleteMapping(value="/eventos-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeEventos(@PathVariable Long id){
		//try {
			eventoService.remover(id);;
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

}
