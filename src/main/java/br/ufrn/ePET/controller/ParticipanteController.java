package br.ufrn.ePET.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Participante;
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
		List<Participante> participantes = participanteService.buscar();
		if (participantes.isEmpty())
			throw new ResourceNotFoundException("Nenhum participante cadastrado");
		//try {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		//} catch (Exception e) {
			///return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/participantes/{id}")
	public ResponseEntity<?> getParticipantes(@PathVariable Long id){
		Participante participantes = participanteService.buscar(id);
		if (participantes.isEspera())
			throw new ResourceNotFoundException("Participante com id "+id+" não encontrado");
		//try {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/participantes-pessoa/{id}")
	public ResponseEntity<?> getParticipantesPessoa(@PathVariable Long id){
		List<Participante> participantes = participanteService.buscarPessoa(id);
		if (participantes.isEmpty())
			throw new ResourceNotFoundException("Não há participações de uma pessoa com id" + id + " no sistema");
		//try {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
}
