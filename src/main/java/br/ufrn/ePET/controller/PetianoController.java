package br.ufrn.ePET.controller;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Petiano;
import br.ufrn.ePET.service.PetianoService;

@RestController
@RequestMapping(value="/api")
public class PetianoController {
	
	private final PetianoService petianoservice;
	
	@Autowired
	public PetianoController(PetianoService petianoservice) {
		this.petianoservice = petianoservice;
	}

	@GetMapping(value="/petianos-atuais")
	@ApiOperation(value = "Retorna todos os petianos atuais do sistema.")
	public ResponseEntity<?> getPetianosAtuais(Pageable pageable){
		Page<Petiano> petianos = petianoservice.buscarAtuais(pageable);
		/*if (petianos.isEmpty())
			throw new ResourceNotFoundException("Nenhum petiano cadastrado!");*/
		//try {
			return new ResponseEntity<>(petianos, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
		
	}
	

	@GetMapping(value="/petianos-antigos")
	@ApiOperation(value = "Retorna todos os petianos egressos do sistema.")
	public ResponseEntity<?> getPetianosAntigos(Pageable pageable){
		Page<Petiano> petianos = petianoservice.buscarAntigos(pageable);
		/*if (petianos.isEmpty())
			throw new ResourceNotFoundException("Nenhum petiano cadastrado!");*/
		//try {
			return new ResponseEntity<>(petianos, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
		
	}

	@GetMapping(value="/petianos/{id}")
	@ApiOperation(value = "Retorna todos os petianos do sistema(atuais e egressos).")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public Petiano getPetianos(@PathVariable Long id){
		Petiano petiano = petianoservice.buscar(id);
		if (petiano == null)
			throw new ResourceNotFoundException("Nenhum petiano com id "+ id +" cadastrado!");
		return petiano;
	}

	@GetMapping(value = "pesquisar-petiano/{search}")
	@ApiOperation(value = "Retorna os dados de um petiano a partir de seu nome ou CPF.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getPetianosPorNomeOuCpf(@ApiParam(value = "Nome ou CPF de um petiano.") @PathVariable String search, Pageable pageable){
		Page<Petiano> petianos = petianoservice.buscarPorNomeOuCpf(search, pageable);
		if(petianos.isEmpty()){
			throw new ResourceNotFoundException("Nenhum petiano encontrado!");
		} else {
			return new ResponseEntity<>(petianos, HttpStatus.OK);
		}
	}

	@PostMapping(value="/petianos-cadastro/{id}")
	@ApiOperation(value = "Cadastra um petiano.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> savePetianos(@ApiParam(value = "id_pessoa de um usuário que será um petiano.") @PathVariable Long id, @Valid @RequestBody Petiano petiano){
		//try{
			petianoservice.salvar(id, petiano);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@DeleteMapping(value="/petianos-remove/{id}")
	@ApiOperation(value = "Torna um petiano em egresso.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> removePetianos(@ApiParam(value = "Id do petiano que irá se tornar egresso.")@PathVariable Long id){
		Petiano petiano = petianoservice.buscar(id);
		if (petiano == null)
			throw new ResourceNotFoundException("Nenhum petiano com id "+ id +" cadastrado!");
		//try {
			petianoservice.remover(id);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}


	@PutMapping(value="/petianos-editar/{id_pessoa}")
	@ApiOperation(value = "Método responsável por editar os dados de um petiano.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> editaPetianos(@ApiParam(value = "Id_pessoa do petiano que irá ser editado.") @PathVariable Long id_pessoa , 
										   @ApiParam(value = "Dados do petiano que será editado.") @Valid @RequestBody Petiano petiano){
		//try{
			petianoservice.editar(id_pessoa, petiano);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
		
	}
	
	@GetMapping(value = "/petianos-pessoa/{id_pessoa}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	//@Secured({"ROLE_tutor"})
	public ResponseEntity<?> getPetianosPorPessoa(@PathVariable Long id_pessoa){
		Petiano petiano = petianoservice.buscarPorPessoa(id_pessoa);
		if (petiano == null)
			throw new ResourceNotFoundException("Nenhum petiano com id de pessoa "+ id_pessoa +" cadastrado!");
		return new ResponseEntity<>(petiano, HttpStatus.OK);
		
	}
	
}
