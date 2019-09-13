package br.ufrn.ePET.controller;

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
		try {
			return new ResponseEntity<>(organizadoresService.buscar(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/organizadores/{id}")
	public ResponseEntity<?> getOrganizadores(@PathVariable Long id){
		try {
			return new ResponseEntity<>(organizadoresService.buscar(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/organizadores-pessoa/{id}")
	public ResponseEntity<?> getOrganizadoresPessoa(@PathVariable Long id){
		try {
			return new ResponseEntity<>(organizadoresService.buscarPessoa(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/organizadores-evento/{id}")
	public ResponseEntity<?> getOrganizadoresEvento(@PathVariable Long id){
		try {
			return new ResponseEntity<>(organizadoresService.buscarEvento(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/organizadores-cadastrar/{id_evento}/{id_pessoa}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> salvarOrganizadores(@PathVariable Long id_evento, @PathVariable Long id_pessoa){
		try {
			organizadoresService.salvar(id_evento, id_pessoa);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/organizadores-remove/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removerOrganizadores(@PathVariable Long id){
		try {
			organizadoresService.remover(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
