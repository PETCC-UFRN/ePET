package br.ufrn.ePET.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.service.CertificadoService;
import br.ufrn.ePET.service.PessoaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value="/api")
public class CertificadoController {
	
	private final CertificadoService certificadoService;
	private final PessoaService pessoaService;

	@Autowired
	public CertificadoController(CertificadoService certificadoService, PessoaService pessoaService) {
		this.certificadoService = certificadoService;
		this.pessoaService = pessoaService;
	}

	
	@GetMapping(value = "/certificado/validar/{hash}")
	@ApiOperation(value = "Método que faz a validação de uma hash de uma declaração.")
	public ResponseEntity<?> getVerificarCertificado(@ApiParam(value = "hash code que está na declaração") @PathVariable String hash){
		certificadoService.verificarCertificado(hash);
		return new ResponseEntity<>( HttpStatus.OK);
	}

	
	@GetMapping(value = "/certificado/validarOrganizador/{hash}")
	@ApiOperation(value = "Método que faz a validação de uma hash de uma declaração.")
	public ResponseEntity<?> getVerificarOrganizadorCertificado(@ApiParam(value = "hash code que está na declaração") @PathVariable String hash){
		certificadoService.verificarOrganizadorCertificado(hash);
		return new ResponseEntity<>( HttpStatus.OK);
	}


	@GetMapping(value = "/certificado/gerar/{id_pessoa}/{id_evento}")
	@ApiOperation(value = "Método que gera uma declaração para determinada pessoa em um determinado evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public HttpEntity<byte[]>  gerarCertificado(@ApiParam(value = "Id da pessoa que está solicitando a declaração.") @PathVariable Long id_pessoa, 
												@ApiParam(value = "Id do evento que o usuário está solitando a declaração.") @PathVariable Long id_evento,
												HttpServletRequest req) throws IOException{
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p == null){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(p.getIdPessoa() != id_pessoa && p.getTipo_usuario().getNome() == "comum" ) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		String fileName = certificadoService.gerarCertificado(id_pessoa,id_evento);
		byte[] arquivo = Files.readAllBytes( Paths.get(fileName));
		HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\"certificado.pdf.pdf\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);
        Files.deleteIfExists(Paths.get(fileName));
        return entity;
//		return new ResponseEntity<>(HttpStatus.OK);	
	}
	


	@GetMapping(value = "/certificado/gerarOrganizador/{id_pessoa}/{id_evento}")
	@ApiOperation(value = "Método que gera uma declaração para determinada pessoa em um determinado evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public HttpEntity<byte[]>  gerarCertificadoOrganizador(@ApiParam(value = "Id da pessoa que está solicitando a declaração.") @PathVariable Long id_pessoa, 
												@ApiParam(value = "Id do evento que o usuário está solitando a declaração.") @PathVariable Long id_evento,
												HttpServletRequest req) throws IOException{
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p == null){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(p.getIdPessoa() != id_pessoa && p.getTipo_usuario().getNome() == "comum" ) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		String fileName = certificadoService.gerarCertificadoOrganizador(id_pessoa,id_evento);
		byte[] arquivo = Files.readAllBytes( Paths.get(fileName));
		HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\"certificado.pdf\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);
        Files.deleteIfExists(Paths.get(fileName));
        return entity;
//		return new ResponseEntity<>(HttpStatus.OK);	
	}

	@PostMapping(value = "/certificado/mudarTemplate/")
	@ApiOperation(value = "Método que faz o upload de um novo template para o certificado.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> mudarTemplate(@ApiParam(value = "arquivo a ser anexado") @RequestParam("file") MultipartFile file) {
		certificadoService.modificarTemplate(file);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
