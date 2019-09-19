package br.ufrn.ePET.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Periodo_Evento;
import br.ufrn.ePET.service.Periodo_EventoService;

@RestController
@RequestMapping(value = "/api")
public class Periodo_EventoController {
	
	private final Periodo_EventoService periodo_EventoService;

	@Autowired
	public Periodo_EventoController(Periodo_EventoService periodo_EventoService) {
		this.periodo_EventoService = periodo_EventoService;
	}
	
	@GetMapping(value = "/periodo-evento")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getPeriodoEvento(){
		List<Periodo_Evento> pe = periodo_EventoService.buscar();
		if (pe.isEmpty())
			throw new ResourceNotFoundException("Nenhum periodo de evento cadastrado");
		//try {
		return new ResponseEntity<>(pe, HttpStatus.OK);
	}
	
	@GetMapping(value = "periodo-evento/{id}")
	public ResponseEntity<?> getPeriodoEvento(@PathVariable Long id){
		Periodo_Evento pe = periodo_EventoService.buscar(id);
		if(pe == null) {
			throw new ResourceNotFoundException("Nenhum periodo de evento encontrado");
		}
		return new ResponseEntity<>(pe, HttpStatus.OK);
	}
	
	@PostMapping(value = "periodo-evento-cadastar/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> salvarPeriodoEvento(@PathVariable Long id, @RequestBody Periodo_Evento pe){
		periodo_EventoService.salvar(id, pe);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "periodo-evento-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removerPeriodoEvento(@PathVariable Long id){
		periodo_EventoService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}