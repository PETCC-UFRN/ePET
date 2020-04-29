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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api")
public class CertificadoController {
	
	private final CertificadoService certificadoService;

	@Autowired
	public CertificadoController(CertificadoService certificadoService) {
		this.certificadoService = certificadoService;
	}

	@GetMapping(value = "/certificado/validar/{hash}")
	@ApiOperation(value = "Método que faz a validação de uma hash de uma declaração.")
	public ResponseEntity<?> getVerificarCertificado(@ApiParam(value = "hash code que está na declaração") @PathVariable String hash){
		certificadoService.verificarCertificado(hash);
		return new ResponseEntity<>( HttpStatus.OK);
	}

	@GetMapping(value = "/certificado/gerar/{id_pessoa}/{id_evento}")
	@ApiOperation(value = "Método que gera uma declaração para determinada pessoa em um determinado evento.")
	//@Secured({"ROLE_tutor", "ROLE_petiano"})
	public HttpEntity<byte[]>  gerarCertificado(@ApiParam(value = "Id da pessoa que está solicitando a declaração.") @PathVariable Long id_pessoa, 
												@ApiParam(value = "Id do evento que o usuário está solitando a declaração.") @PathVariable Long id_evento) throws IOException{
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
