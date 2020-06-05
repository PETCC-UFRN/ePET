package br.ufrn.ePET.controller;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.service.PessoaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Tutoria_Ministrada;
import br.ufrn.ePET.service.Tutoria_MinistradaService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/api")
public class Tutoria_MinistradaController {

	private final Tutoria_MinistradaService tutoria_MinistradaService;
	private final PessoaService pessoaService;
	
	@Autowired
	public Tutoria_MinistradaController(Tutoria_MinistradaService tutoria_MinistradaService, PessoaService pessoaService) {
		this.tutoria_MinistradaService = tutoria_MinistradaService;
		this.pessoaService = pessoaService;
	}
	
	@GetMapping(value="/tutorias-ministradas/{id}")
	@ApiOperation(value = "Método responsável por retornar as tutorias ministradas(Tutorias solicitadas pelos alunos) cadastradas no sistema")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	//@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriaMinistrada(HttpServletRequest  req, @PathVariable Long id){
		Tutoria_Ministrada tutoria_Ministrada = tutoria_MinistradaService.buscar(id);
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(tutoria_Ministrada == null)
			throw new ResourceNotFoundException("Tutoria com o id: "+ id +" não encontrada.");
		if(tutoria_Ministrada.getPessoa().getIdPessoa() != p.getIdPessoa() && p.getTipo_usuario().getNome().equalsIgnoreCase("comum")){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<>(tutoria_Ministrada, HttpStatus.OK);
	}
	
	@GetMapping(value="/tutorias-ministradas")
	@ApiOperation(value = "Método que retorna todas as tutorias ministradas.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradas(Pageable pageable){
		Page<Tutoria_Ministrada> tutorias = tutoria_MinistradaService.buscar(pageable);
		/*if( tutorias.isEmpty() )
			throw new ResourceNotFoundException("Nenhuma tutoria encontrada");*/
		
		return new ResponseEntity<>(tutorias, HttpStatus.OK);
	}

	@GetMapping(value = "/pesquisar-pessoa-tutorias-ministradas/{id}")
	@ApiOperation(value = "Método responsável por retornar as tutorias solicitadas por um determinado usuário passado pelo ID e que está ativa e que ainda irão ocorrer")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	///@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradasPessoa(HttpServletRequest req, @ApiParam(value = "Id da pessoa que terá suas solicitações de tutorias listadas.") @PathVariable Long id, Pageable pageable){
		try{
			Pessoa p = pessoaService.buscarPorEmail(req);
			if(p.getIdPessoa() == id){
				return new ResponseEntity<>(tutoria_MinistradaService.buscarPorPessoa(id, pageable), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/pesquisar-pessoa-tutorias-ministradas-concluidas/{id}")
	@ApiOperation(value = "Método responsável por retornar as tutorias solicitadas por um determinado usuário passado pelo ID e que está ativa e que já foi concluída")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	///@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradasPessoaConcluidas(HttpServletRequest req, @ApiParam(value = "Id da pessoa que terá suas solicitações de tutorias listadas.") @PathVariable Long id, Pageable pageable){
		try{
			Pessoa p = pessoaService.buscarPorEmail(req);
			if(p.getIdPessoa() == id){
				return new ResponseEntity<>(tutoria_MinistradaService.buscarPorPessoaConcluida(id, pageable), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/pesquisar-petiano-tutoria-ministradas")
	@ApiOperation(value = "Método responsável por retornar as tutorias que um petiano esta ministrando.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradasPetiano(HttpServletRequest req, Pageable pageable){
		try{
			Pessoa p = pessoaService.buscarPorEmail(req);
			if(p != null){
				return new ResponseEntity<>(tutoria_MinistradaService.buscarPorPetiano(p.getIdPessoa(),pageable), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/pesquisar-pessoa-tutorias-ministradas-ina/{id}")
	@ApiOperation(value = "Método responsável por retornar as tutorias solicitadas por um determinado usuário passado pelo ID e que está inativa")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> getTutoriasMinistradasPessoaInativo(@ApiParam(value = "Id da pessoa que terá suas solicitações de tutorias inativas listadas.") @PathVariable Long id, HttpServletRequest req, Pageable pageable){
		try{
			Pessoa p = pessoaService.buscarPorEmail(req);
			if(p.getTipo_usuario().getNome() == "comum" || p.getTipo_usuario().getNome() == "petiano" ) {
				if(p.getIdPessoa() != id) {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
			}
			return new ResponseEntity<>(tutoria_MinistradaService.buscarPorPessoaInativa(id, pageable), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/pesquisar-tutoria-tutorias-ministradas/{id}")
	@ApiOperation(value = "Método responsável por retornar os dados de uma tutoria ministrada a partir de um ID.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradasTutoria(@ApiParam(value = "Id da tutoria ministrada") @PathVariable Long id, Pageable pageable){
		try{
			return new ResponseEntity<>(tutoria_MinistradaService.buscarPorTutoria(id, pageable), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/tutorias-ministradas-cadastro/{id_pessoa}/{id_tutoria}")
	@ApiOperation(value = "Método responsável por criar uma Tutoria Ministrada. Uma tutoria ministrada é uma solicitação de tutoria"
			+ " por parte um usuário Comum. Exemplo: há uma tutoria da disciplina cadastrada com ID 3, o usuário de ID_PESSOA 10 quer"
			+ " solicitar essa tutoria, portanto basta passar /tutorias-ministradas-cadastro/10/3.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> salvarTutoriasMinistradas(@ApiParam(value = "ID da PESSOA(usuário comum) que está solicitando uma tutoria", required = true) @PathVariable Long id_pessoa, 
			 										   @ApiParam(value = "ID da tutoria a ser solicitada", required = true) @PathVariable Long id_tutoria){
		Tutoria_Ministrada tutoria_ministrada = new Tutoria_Ministrada();
		tutoria_MinistradaService.salvar(id_pessoa, id_tutoria, tutoria_ministrada);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value="/tutorias-ministradas-ativar/{id_tutoria_ministrada}/{data_da_tutoria}")
	@ApiOperation(value = "Método responsável por permitir um petiano ativar uma tutoria ministrada solicidata a ele. Isto é, quando um usuário "
			+ "comum solicita uma tutoria, o petiano deve entrar em contato e combinar uma data, quando isso ocorrer, o petiano deve então"
			+ " ativar essa tutoria e passar a data a qual ela ocorrerá.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> ativarTutoriasMinistradas(@ApiParam(value = "Id da tutoria ministrada a ser ativada")@PathVariable Long id_tutoria_ministrada,
													   @ApiParam(value = "Data a qual irá ocorrer a tutoria", required = true) @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_da_tutoria){
		tutoria_MinistradaService.ativar(id_tutoria_ministrada, data_da_tutoria);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
