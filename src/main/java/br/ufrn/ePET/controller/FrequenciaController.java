package br.ufrn.ePET.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@ApiOperation(value = "Método que retorna todas as frequencias cadastradas.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getFrequencia(Pageable pageable){
		Page<Frequencia> f = frequenciaService.buscar(pageable);
		/*if(f.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma frequencia achada");
		}*/
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	@GetMapping(value = "/frequencia-evento/{id_evento}")
	@ApiOperation(value = "Método que retorna todas as frequencias cadastradas.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getFrequencia(@ApiParam(value = "Id da frequência") @PathVariable Long id_evento, Pageable pageable){
		Page<Frequencia> f = frequenciaService.buscarPorEvento(id_evento, pageable);
		if(f == null || f.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma frequencia encontrada para o evento solicitado");
		}
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/frequencia/{id}")
	@ApiOperation(value = "Método que faz retorna a frequencia a partir de seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getFrequencia(@ApiParam(value = "Id da frequência") @PathVariable Long id){
		Frequencia f = frequenciaService.buscar(id);
		if(f == null) {
			throw new ResourceNotFoundException("Frequencia com id "+id+" não encontrada");
		}
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	@GetMapping(value = "/frequencia-pessoa/{id_participante}/{id_evento}")
	@ApiOperation(value = "Método que busca a frequencia de um participante do evento pelo seu ID. Esse método retorna a Assiduidade do participante no evento passado")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getFrequenciaPessoa(@ApiParam(value = "Id do participante que se busca a frequencia") @PathVariable Long id_participante, 
												 @ApiParam(value = "Id do evento que se dejesa solicitar as frequencias") @PathVariable Long id_evento){
		return new ResponseEntity<>(frequenciaService.buscarPorParticipante(id_participante, id_evento), HttpStatus.OK);
	}
	
	@PostMapping(value = "/frequencia-cadastrar/{id_periodo_evento}/{id_participante}")
	@ApiOperation(value = "Método que cadastra uma nova frequencia.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> salvarFrequencia(@ApiParam(value = "Id do periodo a ser confirmada a presença") @PathVariable Long id_periodo_evento, 
											  @ApiParam(value= "Id do participante") @PathVariable Long id_participante, 
											  @ApiParam(value= "Dados refenrente a essa frequencia ") @RequestBody Frequencia f){
		frequenciaService.salvar(id_periodo_evento, id_participante, f);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/frequencia-remove/{id}")
	@ApiOperation(value = "Método que remove uma frequencia.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> removerFrequencia(@ApiParam(value = "Id da frequencia a ser removida") @PathVariable Long id){
		frequenciaService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
