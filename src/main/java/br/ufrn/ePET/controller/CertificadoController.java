package br.ufrn.ePET.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.service.CertificadoService;

@RestController
@RequestMapping(value="/api")
public class CertificadoController {
	
	private final CertificadoService certificadoService;

	@Autowired
	public CertificadoController(CertificadoService certificadoService) {
		this.certificadoService = certificadoService;
	}

	@GetMapping(value = "/certificado/validar/{hash}/{nome}/{cpf}")
	public ResponseEntity<?> getVerificarCertificado(@PathVariable String hash, @PathVariable String nome, 
			@PathVariable String cpf){
		certificadoService.verificarCertificado(nome, cpf, hash);
		return new ResponseEntity<>( HttpStatus.OK);
	}

	@GetMapping(value = "/certificado/gerar/{id_pessoa}/{id_evento}")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public HttpEntity<byte[]>  gerarCertificado(@PathVariable Long id_pessoa, @PathVariable Long id_evento) throws IOException{
		String fileName = certificadoService.gerarCertificado(id_pessoa,id_evento);
		byte[] arquivo = Files.readAllBytes( Paths.get(fileName));
		HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\"certificado.pdf\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);
        Files.deleteIfExists(Paths.get(fileName));
        return entity;
//		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	

}
