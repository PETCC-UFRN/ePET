package br.ufrn.ePET.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;*/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Petiano;
import br.ufrn.ePET.repository.PetianoRepository;
import br.ufrn.ePET.service.PetianoService;

@RestController
@RequestMapping(value="/api")
public class PetianoController {
	
	private final PetianoService petianoservice;
	
	@Autowired
	public PetianoController(PetianoService petianoservice) {
		this.petianoservice = petianoservice;
	}

	@GetMapping(value="/petianos")
	//@PreAuthorize("hasAnyRole('ADMIN', 'TUTOR')")
	public List<Petiano> getPetianos(){
		return petianoservice.buscar();
	}

	@GetMapping(value="/petianos/{id}")
	public Petiano getPetianos(@PathVariable Long id){
		return petianoservice.buscar(id);
	}

	@PostMapping(value="/petianos-cadastro/{id}")
	public ResponseEntity<?> savePetianos(@PathVariable Long id, @RequestBody Petiano petiano){
		petianoservice.salvar(id, petiano);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value="/petianos-remove/{id}")
	public ResponseEntity<?> removePetianos(@PathVariable Long id){
		petianoservice.remover(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PostMapping(value="/petianos-editar/{id}")
	public ResponseEntity<?> editaPetianos(@PathVariable Long id, @RequestBody Petiano petiano){
		petianoservice.editar(id, petiano);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
