package br.ufrn.ePET.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.service.PagamentoService;

@RestController
@RequestMapping(value = "/api")
public class PagamentoController {

	private final PagamentoService pagamentoService;

	@Autowired
	public PagamentoController(PagamentoService pagamentoService) {
		this.pagamentoService = pagamentoService;
	}
	
	@GetMapping(value = "/criar-pagamento/{id_pessoa}/{id_evento}")
	public ResponseEntity<?> pagar(@PathVariable Long id_pessoa, @PathVariable Long id_evento){
		String link = pagamentoService.criarPagamento(id_evento, id_pessoa);
		return new ResponseEntity<>(link, HttpStatus.OK);
	}
	
	@GetMapping(value = "/redirecionamento-pagamento")
	public ResponseEntity<?> redirecionamento(@RequestParam("transaction_id") String transaction_id){
		pagamentoService.redirectPagseguro(transaction_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/verificar-status/{reference}")
	public ResponseEntity<?> verificar(@PathVariable String reference){
		try {
			return new ResponseEntity<>(pagamentoService.verificarStatusPagamento(reference), HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
}
