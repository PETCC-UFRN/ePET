package br.ufrn.ePET.controller;

import io.swagger.annotations.ApiImplicitParam;
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
import br.ufrn.ePET.models.Periodo_Evento;
import br.ufrn.ePET.service.Periodo_EventoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api")
public class Periodo_EventoController {
	
	private final Periodo_EventoService periodo_EventoService;

	@Autowired
	public Periodo_EventoController(Periodo_EventoService periodo_EventoService) {
		this.periodo_EventoService = periodo_EventoService;
	}
	
	@GetMapping(value = "/periodo-evento")
	@ApiOperation(value = "Método que retorna todos os períodos de evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getPeriodoEvento(Pageable pageable){
		Page<Periodo_Evento> pe = periodo_EventoService.buscar(pageable);
		/*if (pe.isEmpty())
			throw new ResourceNotFoundException("Nenhum periodo de evento cadastrado");*/
		//try {
		return new ResponseEntity<>(pe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/periodo-evento/{id}")
	@ApiOperation(value = "Método que retona os periodos de evento a partir de um ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getPeriodoEvento(@ApiParam(value = "id do periodo_evento a ser solicitado") @PathVariable Long id){
		Periodo_Evento pe = periodo_EventoService.buscar(id);
		if(pe == null) {
			throw new ResourceNotFoundException("Nenhum periodo de evento encontrado");
		}
		return new ResponseEntity<>(pe, HttpStatus.OK);
	}
	
	@ApiOperation(value = "buscar periodo de eventos pelo id de um evento específico")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@GetMapping(value = "/periodo-evento-buscar/{id}")
	public ResponseEntity<?> getPeriodoEventoBuscar(@ApiParam(value = "Id do evento a ser solicitado os periodos") @PathVariable Long id_evento, Pageable pageable){
		Page<Periodo_Evento> pe = periodo_EventoService.buscarPorEvento(id_evento, pageable);
		if (pe.isEmpty())
			throw new ResourceNotFoundException("Nenhum periodo de evento com id de evento " + id_evento + " cadastrado");
		return new ResponseEntity<>(pe, HttpStatus.OK);
	}
	
	@PostMapping(value = "periodo-evento-cadastar/{id}")
	@ApiOperation(value = "Método que salva um período de evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> salvarPeriodoEvento(@ApiParam(value = "Id do evento a ser salvo o periodo") @PathVariable Long id, @RequestBody Periodo_Evento pe){
		periodo_EventoService.salvar(id, pe);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "periodo-evento-remove/{id}")
	@ApiOperation(value = "Remove um período-evento pelo seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removerPeriodoEvento(@ApiParam(value = "id do periodo-evento") @PathVariable Long id){
		periodo_EventoService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
