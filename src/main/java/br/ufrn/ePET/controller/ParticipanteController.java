package br.ufrn.ePET.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.service.ParticipanteService;

@RestController
@RequestMapping(value = "/api")
public class ParticipanteController {

	private final ParticipanteService participanteService;

	@Autowired
	public ParticipanteController(ParticipanteService participanteService) {
		this.participanteService = participanteService;
	}
	
	@GetMapping(value = "/participantes")
	public ResponseEntity<?> getParticipantes(){
		try {
			return new ResponseEntity<>(participanteService.buscar(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/participantes/{id}")
	public ResponseEntity<?> getParticipantes(@PathVariable Long id){
		try {
			return new ResponseEntity<>(participanteService.buscar(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/participantes-pessoa/{id}")
	public ResponseEntity<?> getParticipantesPessoa(@PathVariable Long id){
		try {
			return new ResponseEntity<>(participanteService.buscarPessoa(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
