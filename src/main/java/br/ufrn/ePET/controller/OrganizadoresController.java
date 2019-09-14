package br.ufrn.ePET.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.ufrn.ePET.models.Organizadores;
import br.ufrn.ePET.service.OrganizadoresService;

@RestController
@RequestMapping(value = "/api")
public class OrganizadoresController {
	
	private final OrganizadoresService organizadoresService;
	
	@Autowired
	public OrganizadoresController(OrganizadoresService organizadoresService) {
		this.organizadoresService = organizadoresService;
	}
	
	
	@GetMapping(value = "/organizadores")
	public ResponseEntity<?> getOrganizadores(){
		List<Organizadores> organizadores = organizadoresService.buscar();
		if (organizadores.isEmpty())
			throw new ResourceNotFoundException("Nenhum organizador cadastrado.");
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/organizadores/{id}")
	public ResponseEntity<?> getOrganizadores(@PathVariable Long id){
		Organizadores organizadores =  organizadoresService.buscar(id);
		if(organizadores == null)
			throw new ResourceNotFoundException("Nenhum organizador com id "+ id+" encontrado");
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/organizadores-pessoa/{id}")
	public ResponseEntity<?> getOrganizadoresPessoa(@PathVariable Long id){
		List<Organizadores> organizadores = organizadoresService.buscarPessoa(id);
		if(organizadores.isEmpty())
			throw new ResourceNotFoundException("Pessoa de id "+ id+ " não organiza nenhum evento");
		
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/organizadores-evento/{id}")
	public ResponseEntity<?> getOrganizadoresEvento(@PathVariable Long id){
		List<Organizadores> organizadores = organizadoresService.buscarEvento(id);
		if(organizadores.isEmpty())
			throw new ResourceNotFoundException("Evento de id "+ id+ " não possui nenhum organizador");
		//try {
			return new ResponseEntity<>(organizadores, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@PostMapping(value = "/organizadores-cadastrar/{id_evento}/{id_pessoa}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> salvarOrganizadores(@PathVariable Long id_evento, @PathVariable Long id_pessoa){
		//try {
			organizadoresService.salvar(id_evento, id_pessoa);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//System.out.println(e);
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@DeleteMapping(value = "/organizadores-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removerOrganizadores(@PathVariable Long id){
		//try {
			organizadoresService.remover(id);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
}