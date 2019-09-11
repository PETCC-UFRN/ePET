package br.ufrn.ePET.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
		try {
			return new ResponseEntity<>(petianoservice.buscarAtuais(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	

	@GetMapping(value="/petianos-antigos")
	public ResponseEntity<?> getPetianosAntigos(){
		try {
			return new ResponseEntity<>(petianoservice.buscarAntigos(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping(value="/petianos/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public Petiano getPetianos(@PathVariable Long id){
		return petianoservice.buscar(id);
	}

	@PostMapping(value="/petianos-cadastro/{id}")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> savePetianos(@PathVariable Long id, @RequestBody Petiano petiano){
		try{
			petianoservice.salvar(id, petiano);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value="/petianos-remove/{id}")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> removePetianos(@PathVariable Long id){
		try {
			petianoservice.remover(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@PostMapping(value="/petianos-editar/{id}")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> editaPetianos(@PathVariable Long id, @RequestBody Petiano petiano){
		try{
			petianoservice.editar(id, petiano);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
}
