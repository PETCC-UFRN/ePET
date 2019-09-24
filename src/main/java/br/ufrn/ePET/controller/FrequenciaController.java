package br.ufrn.ePET.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Frequencia;
import br.ufrn.ePET.service.FrequenciaService;

@RestController
@RequestMapping(value = "/api")
public class FrequenciaController {
	
	private final FrequenciaService frequenciaService;
	
	@Autowired
	public FrequenciaController(FrequenciaService frequenciaService) {
		this.frequenciaService = frequenciaService;
	}
	
	@GetMapping(value = "/frequencia")
	public ResponseEntity<?> getFrequencia(){
		List<Frequencia> f = frequenciaService.buscar();
		if(f.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma frequencia achada");
		}
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	@GetMapping(value = "/frequencia/{id}")
	public ResponseEntity<?> getFrequencia(@PathVariable Long id){
		Frequencia f = frequenciaService.buscar(id);
		if(f == null) {
			throw new ResourceNotFoundException("Frequencia com id "+id+" não encontrada");
		}
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	@GetMapping(value = "frequencia-pessoa/{id}")
	public ResponseEntity<?> getFrequenciaPessoa(@PathVariable Long id){
		return new ResponseEntity<>(frequenciaService.buscarPorParticipante(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "frequencia-cadastrar/{id_periodo_evento}/{id_participante}")
	public ResponseEntity<?> salvarFrequencia(@PathVariable Long id_periodo_evento, 
			@PathVariable Long id_participante, @RequestBody Frequencia f){
		frequenciaService.salvar(id_periodo_evento, id_participante, f);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "frequencia-remove/{id}")
	public ResponseEntity<?> removerFrequencia(@PathVariable Long id){
		frequenciaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
