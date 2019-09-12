package br.ufrn.ePET.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class UsuarioController {
	
	private UsuarioService usuario;
	
	@Autowired
	public UsuarioController(UsuarioService user) {
		this.usuario = 
	}
	
	@GetMapping(value="/usuarios")
	public ResponseEntity<?> getUsuarios(){
		try {
			return new ResponseEntity<T>(usua)
		} catch(Exception e) {
			
		}
	}
	

}
