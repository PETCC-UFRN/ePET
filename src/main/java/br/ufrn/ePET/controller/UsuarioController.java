package br.ufrn.ePET.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import br.ufrn.ePET.models.AuthDTO;
import br.ufrn.ePET.models.EmailDTO;
import br.ufrn.ePET.models.Pessoa;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
import br.ufrn.ePET.models.Usuario;
import br.ufrn.ePET.service.PessoaService;
import br.ufrn.ePET.service.Tipo_UsuarioService;
import br.ufrn.ePET.service.UsuarioService;

@RestController
@RequestMapping(value="/api")
public class UsuarioController {
	
	private UsuarioService usuarioService;
	private PessoaService pessoaService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService, PessoaService pessoaService,
			Tipo_UsuarioService tuService) {
		this.usuarioService = usuarioService;
		this.pessoaService = pessoaService;
	}
	
	@GetMapping(value = "/usuarios")
	@ApiOperation(value = "Retorna todos os usuários do sistema.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getUsuarios(Pageable pageable){
		Page<Usuario> page = usuarioService.buscar(pageable);
		if(page.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum usuário encontrado");
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	@GetMapping(value="/usuarios/{id}")
	@ApiOperation(value = "Método que busca um usuário do sistema com base em seu id.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getUsuarios(@ApiParam(value = "Id do usuario a ser solicitado") @PathVariable Long id){
		Usuario usuario = usuarioService.buscar(id);
		if (usuario == null)
			throw new ResourceNotFoundException("Usuario com id "+ id+" não encontrado.");
		//try {
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		//} catch(Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	/*@PostMapping(value="/sign-up")
	public ResponseEntity<?> saveUsuarios(@Valid @RequestBody UsuarioDTO usuarioDTO){
		//try {
			usuarioService.signup(usuarioDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch(Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}*/

	@PostMapping(value="/usuarios-atualizar/")
	@ApiOperation(value = "Método que atualiza os dados de um usuário.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> updateUsuarios(HttpServletRequest req, @Valid @RequestBody AuthDTO authDTO){
		//try {
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p.getUsuario().getEmail().equalsIgnoreCase(authDTO.getEmail()) || p.getTipo_usuario().getNome().equalsIgnoreCase("tutor")){
			usuarioService.atualizar(authDTO.getEmail(), authDTO.getSenha());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		//} catch(Exception e) {
		//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@PostMapping(value = "/usuarios-atualizar-email/")
	@ApiOperation(value = "Método que atualiza o email de um usuário")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> atualizarEmail(HttpServletRequest req, @Valid @RequestBody EmailDTO emailDTO){
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p.getUsuario().getEmail().equalsIgnoreCase(emailDTO.getEmail()) || p.getTipo_usuario().getNome().equalsIgnoreCase("tutor")){
			usuarioService.atualizarEmail(p, emailDTO.getEmail());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	

}
