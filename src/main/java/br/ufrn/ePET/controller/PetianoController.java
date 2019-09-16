package br.ufrn.ePET.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<?> getPetianosAtuais(){
		List<Petiano> petianos = petianoservice.buscarAtuais();
		if (petianos.isEmpty())
			throw new ResourceNotFoundException("Nenhum petiano cadastrado!");
		//try {
			return new ResponseEntity<>(petianos, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
		
	}
	

	@GetMapping(value="/petianos-antigos")
	public ResponseEntity<?> getPetianosAntigos(){
		List<Petiano> petianos = petianoservice.buscarAntigos();
		if (petianos.isEmpty())
			throw new ResourceNotFoundException("Nenhum petiano cadastrado!");
		//try {
			return new ResponseEntity<>(petianos, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
		
	}

	@GetMapping(value="/petianos/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public Petiano getPetianos(@PathVariable Long id){
		Petiano petiano = petianoservice.buscar(id);
		if (petiano == null)
			throw new ResourceNotFoundException("Nenhum petiano com id "+ id +" cadastrado!");
		return petiano;
	}

	@PostMapping(value="/petianos-cadastro/{id}")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> savePetianos(@PathVariable Long id, @Valid @RequestBody Petiano petiano){
		//try{
			petianoservice.salvar(id, petiano);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@DeleteMapping(value="/petianos-remove/{id}")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> removePetianos(@PathVariable Long id){
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
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> editaPetianos(@PathVariable Long id_pessoa , @Valid @RequestBody Petiano petiano){
		//try{
			petianoservice.editar(id_pessoa, petiano);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
		
	}
	
}
