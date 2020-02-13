package br.ufrn.ePET.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Tutoria_Ministrada;
import br.ufrn.ePET.service.Tutoria_MinistradaService;

@RestController
@RequestMapping(value="/api")
public class Tutoria_MinistradaController {

	private final Tutoria_MinistradaService tutoria_MinistradaService;
	
	@Autowired
	public Tutoria_MinistradaController(Tutoria_MinistradaService tutoria_MinistradaService) {
		this.tutoria_MinistradaService = tutoria_MinistradaService;
	}
	
	@GetMapping(value="/tutorias-ministradas/{id}")
	//@Secured({"ROLE_tutor", "ROLE_comum", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriaMinistrada(@PathVariable Long id){
		Tutoria_Ministrada tutoria_Ministrada = tutoria_MinistradaService.buscar(id);
		if(tutoria_Ministrada == null)
			throw new ResourceNotFoundException("Tutoria com o id: "+ id +" não encontrada.");
		
		return new ResponseEntity<>(tutoria_Ministrada, HttpStatus.OK);
	}
	
	@GetMapping(value="/tutorias-ministradas")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradas(Pageable pageable){
		Page<Tutoria_Ministrada> tutorias = tutoria_MinistradaService.buscar(pageable);
		if( tutorias.isEmpty() )
			throw new ResourceNotFoundException("Nenhuma tutoria encontrada");
		
		return new ResponseEntity<>(tutorias, HttpStatus.OK);
	}

	@GetMapping(value = "/pesquisar-pessoa-tutorias-ministradas/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradasPessoa(@PathVariable Long id, Pageable pageable){
		try{
			return new ResponseEntity<>(tutoria_MinistradaService.buscarPorPessoa(id, pageable), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/pesquisar-tutoria-tutorias-ministradas/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getTutoriasMinistradasTutoria(@PathVariable Long id, Pageable pageable){
		try{
			return new ResponseEntity<>(tutoria_MinistradaService.buscarPorTutoria(id, pageable), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/tutorias-ministradas-cadastro/{id_pessoa}/{id_tutoria}")
	public ResponseEntity<?> salvarTutoriasMinistradas(@PathVariable Long id_pessoa, @PathVariable Long id_tutoria){
		Tutoria_Ministrada tutoria_ministrada = new Tutoria_Ministrada();
		System.out.println("rodando aq");
		tutoria_MinistradaService.salvar(id_pessoa, id_tutoria, tutoria_ministrada);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value="/tutorias-ministradas-ativar/{id_tutoria_ministrada}")
	public ResponseEntity<?> ativarTutoriasMinistradas(@PathVariable Long id_tutoria_ministrada){
		tutoria_MinistradaService.ativar(id_tutoria_ministrada);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
