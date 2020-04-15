package br.ufrn.ePET.controller;

import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getFrequencia(Pageable pageable){
		Page<Frequencia> f = frequenciaService.buscar(pageable);
		/*if(f.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma frequencia achada");
		}*/
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	@GetMapping(value = "/frequencia/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getFrequencia(@PathVariable Long id){
		Frequencia f = frequenciaService.buscar(id);
		if(f == null) {
			throw new ResourceNotFoundException("Frequencia com id "+id+" não encontrada");
		}
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	@GetMapping(value = "frequencia-pessoa/{id_participante}/{id_evento}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getFrequenciaPessoa(@PathVariable Long id_participante, @PathVariable Long id_evento){
		return new ResponseEntity<>(frequenciaService.buscarPorParticipante(id_participante, id_evento), HttpStatus.OK);
	}
	
	@PostMapping(value = "frequencia-cadastrar/{id_periodo_evento}/{id_participante}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> salvarFrequencia(@PathVariable Long id_periodo_evento, 
			@PathVariable Long id_participante, @RequestBody Frequencia f){
		frequenciaService.salvar(id_periodo_evento, id_participante, f);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "frequencia-remove/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> removerFrequencia(@PathVariable Long id){
		frequenciaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
