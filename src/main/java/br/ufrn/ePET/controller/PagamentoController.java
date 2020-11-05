package br.ufrn.ePET.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufrn.ePET.service.PagamentoService;

@RestController
@RequestMapping(value = "/api")
public class PagamentoController {

	private final PagamentoService pagamentoService;

	@Autowired
	public PagamentoController(PagamentoService pagamentoService) {
		this.pagamentoService = pagamentoService;
	}
	
	@GetMapping(value = "/criar-pagamento/{id_participante}")
	@ApiOperation(value = "Método que cria uma ordem de pagamento para o evento.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> pagar(@ApiParam(value = "Id do participante que está solicitando o pagamento") @PathVariable Long id_participante){
		String link = pagamentoService.criarPagamento(id_participante);
		return new ResponseEntity<>(link, HttpStatus.OK);
	}
	
	@GetMapping(value = "/redirecionamento-pagamento/")
	@ApiOperation(value = "Método que redireciona a requisição de pagamento.")
	public ResponseEntity<?> redirecionamento(@ApiParam(value = "id da transação.") @RequestParam("transaction_id") String transaction_id){
		pagamentoService.redirectPagseguro(transaction_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/verificar-status/{id_participante}")
	@ApiOperation(value = "Método que retorna o status de pagamento de um participante.")
	public ResponseEntity<?> verificar(@PathVariable Long id_participante){
		return new ResponseEntity<>(pagamentoService.verificarStatusPagamento(id_participante), HttpStatus.OK);
	}
	
	@PostMapping(value = "/pagseguro-notificacao/")
	@ApiOperation(value = "Método que verifica o status do pagamento.")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<?> verificar(@ApiParam(value = "codigo da notificação") @RequestParam("notificationCode") String nCode, 
									   @ApiParam(value = "tipo de notificação (Váriável nao usada, checkar!!!!)") @RequestParam("notificationType") String nType){
		//System.out.println();
		pagamentoService.verifiarStatusPagamento(nCode, nType);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
