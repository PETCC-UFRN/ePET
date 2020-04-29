package br.ufrn.ePET.controller;

import br.ufrn.ePET.models.Pessoa;
import com.sun.mail.iap.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
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
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getParticipantesPessoa(@PathVariable Long id, Pageable pageable){
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
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getParticipantesPorPessoa(@PathVariable String search, Pageable pageable){
		Page<Participante> participantes = participanteService.buscarPorNomeOuCpfPessoa(search, pageable);
		if(participantes.isEmpty()){
			throw new ResourceNotFoundException("Nenhuma pessoa encontrada com esse nome");
		} else {
			return new ResponseEntity<>(participantes, HttpStatus.OK);
		}
	}

	@GetMapping(value = "pesquisar-evento-participante/{search}")
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
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> salvarParticipantes(@PathVariable Long id_evento, @PathVariable Long id_pessoa){
		participanteService.salvar(id_evento, id_pessoa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/participantes-remove/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> deleteParticipantes(@PathVariable Long id){
		participanteService.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/participantes-confirmar/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> confirmarParticipantes(@PathVariable Long id){
		participanteService.confirmar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
