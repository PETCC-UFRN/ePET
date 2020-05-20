package br.ufrn.ePET.controller;

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
	@ApiOperation(value = "Método responsável por retornar os participantes de todos os eventos.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getParticipantes(Pageable pageable){
		Page<Participante> participantes = participanteService.buscar(pageable);
		/*if (participantes.isEmpty())
			throw new ResourceNotFoundException("Nenhum participante cadastrado");*/
		//try {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		//} catch (Exception e) {
			///return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/participantes/{id}")
	@ApiOperation(value = "Método que busca o participante através de seu ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getParticipantes(@ApiParam(value = "Id do participante") @PathVariable Long id){
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
	@ApiOperation(value = "Método que busca as participações em evento de uma Pessoa através do ID da PESSOA.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> getParticipantesPessoa(@ApiParam(value = "ID da PESSOA a ser solicitada as participações") @PathVariable Long id, Pageable pageable){
		Page<Participante> participantes = participanteService.buscarPessoa(id, pageable);
		if (participantes.isEmpty())
			throw new ResourceNotFoundException("Não há participações de uma pessoa com id" + id + " no sistema");
		//try {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@GetMapping(value = "/participantes-evento/{id}")
	@ApiOperation(value = "Método que retorna todos os participantes de um evento à partir do id do evento")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getParticipantesEvento(@PathVariable Long id, Pageable pageable){
		Page<Participante> participantes = participanteService.buscarPorEvento(id, pageable);
		return new ResponseEntity<>(participantes, HttpStatus.OK);
	}


	@GetMapping(value = "pesquisar-pessoa-participante/{search}")
	@ApiOperation(value = "Método que busca as participações em evento de uma Pessoa através do Nome ou CPF da PESSOA.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> getParticipantesPorPessoa(@ApiParam(value = "Nome ou CPF da pessoa a ser solicitada" )@PathVariable String search, Pageable pageable){
		Page<Participante> participantes = participanteService.buscarPorNomeOuCpfPessoa(search, pageable);
		if(participantes.isEmpty()){
			throw new ResourceNotFoundException("Nenhuma pessoa encontrada com esse nome");
		} else {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		}
	}

	@GetMapping(value = "pesquisar-evento-participante/{search}")
	@ApiOperation(value = "Método responsável por retornar os participantes de um evento através do Titulo do evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getParticipantesPorEvento(@PathVariable String search, Pageable pageable){
		Page<Participante> participantes = participanteService.buscarPorTituloEvento(search, pageable);
		if(participantes.isEmpty()){
			throw new ResourceNotFoundException("Nenhuma pessoa encontrada com esse nome");
		} else {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/participantes-cadastrar/{id_evento}/{id_pessoa}")
	@ApiOperation(value = "Método que cadastra uma pessoa a participar de um evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> salvarParticipantes(@ApiParam(value = "id do evento que se deseja participar") @PathVariable Long id_evento,
												 @ApiParam(value = "id da pessoa que quer participar do evento") @PathVariable Long id_pessoa){
		participanteService.salvar(id_evento, id_pessoa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/participantes-remove/{id}")
	@ApiOperation(value = "Remove a participação de uma pessoa passada por ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> deleteParticipantes(@ApiParam(value = "id participante a ser removido") @PathVariable Long id){
		participanteService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/participantes-confirmar/{id}")
	@ApiOperation(value = "Método Confirma a participação de uma pessoa ao evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> confirmarParticipantes(@PathVariable Long id){
		participanteService.confirmar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
