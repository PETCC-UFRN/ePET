package br.ufrn.ePET.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "/api")
@RequestMapping(value="/api")
public class PessoaController {
	
	private final PessoaService pessoaservice;
	
	@Autowired
	public PessoaController(PessoaService pessoaservice) {
		this.pessoaservice = pessoaservice;
	}
	
	@ApiOperation(value = "Retorna todas as pessoas cadastradas")
	@GetMapping(value="/pessoas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getPessoas(Pageable pageable){
		Page<Pessoa> pessoas = pessoaservice.buscar(pageable);
		/*if (pessoas.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma pessoa cadastrada!");
		}*/
		//try {
			return new ResponseEntity<>(pessoas, HttpStatus.OK);
		//} catch (Exception e) {
		//	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
		
	}
	
	@GetMapping(value = "/pessoas-usuario")
	@ApiOperation(value = "Método que retorna os dados da pessoa atualmente logada e que esá fazendo a requisição.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> getPessoaUsuario(HttpServletRequest req){
		Pessoa p = pessoaservice.buscarPorEmail(req);
		if(p == null) {
			throw new ResourceNotFoundException("Não foi possível adquirir o id do participante atual");
		}
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@GetMapping(value="/pessoas/{id}")
	@ApiOperation(value = "Método que retorna os dados de uma pessoa com base em um ID")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> getPessoas(@ApiParam(value = "Id de uma pessoa") @PathVariable Long id, 
										HttpServletRequest  req){
		Pessoa pessoa = pessoaservice.buscarPorEmail(req);
		if (pessoa == null) {
			throw new ResourceNotFoundException("Pessoa com id " + id + " não encontrada.");
		}
		if(pessoa.getIdPessoa() != id){
			throw new ResourceNotFoundException("Tentativa de acesso a um usuário diferente do seu!");
		}

		return new ResponseEntity<>(pessoa, HttpStatus.OK);
	}

	@PostMapping(value="/pessoas-atualizar/")
	@ApiOperation(value = "Método que atualiza os dados da pessoa que está logada.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> atualizar(HttpServletRequest req, @Valid @RequestBody Pessoa pessoa){
		Pessoa p = pessoaservice.buscarPorEmail(req);
		pessoa.setIdPessoa(p.getIdPessoa());
		pessoaservice.salvar(p.getTipo_usuario().getIdTipo_usuario(), p.getUsuario().getidUsuario(), pessoa , 0);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping(value = "pesquisar-pessoa/{search}")
	@ApiOperation(value = "Método que pesquisa uma pessoa por nome ou CPF.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getPessoasPorNomeOuCPF(@ApiParam(value = "CPF ou nome da pessoa a ser solicitada.") @PathVariable String search, Pageable pageable){
		Page<Pessoa> pessoas = pessoaservice.buscarPorNomeOrCpf(search, pageable);
		if (pessoas.isEmpty()){
			throw  new ResourceNotFoundException("nenhuma pessoa encontrada");
		} else {
			return new ResponseEntity<>(pessoas, HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/pessoas-cadastro-atualizar/{id_tipo}/{id_usuario}")
	@ApiOperation(value = "Método que cadastra uma pessoa no sisterma")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> savePessoas(@ApiParam(value = "Id do tipo de usuário que essa pessoa é.") @PathVariable Long id_tipo, 
			@ApiParam(value = "id do usuário que esta pessoa está relacionada, isto é, o que contém seu email e senha." )@PathVariable Long id_usuario, @Valid @RequestBody Pessoa pessoa, HttpServletRequest req){
		//try {
			Pessoa p = pessoaservice.buscarPorEmail(req);
			if(p.getTipo_usuario().getNome().equalsIgnoreCase("tutor")){
				pessoaservice.salvar(id_tipo, id_usuario,pessoa, 1);
			} else {
				pessoaservice.salvar(id_tipo, id_usuario, pessoa, 2);
			}

			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
}
