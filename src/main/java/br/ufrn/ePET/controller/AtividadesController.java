package br.ufrn.ePET.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.ePET.models.Atividades;
import br.ufrn.ePET.service.AtividadesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api")
public class AtividadesController {

	private AtividadesService atividadesService;

	@Autowired
	public AtividadesController(AtividadesService atividadesService) {
		this.atividadesService = atividadesService;
	}

    @GetMapping(value = "/atividades")
	@ApiOperation(value = "Método que busca todas as atividades do PET.")
	public ResponseEntity<?> buscarAtividades(){
    	List<Atividades> latv = atividadesService.buscarTodos();
		if (latv.isEmpty() || latv == null ) {
			throw new ResourceNotFoundException("Nenhuma atividade cadastrada");
		}
		return new ResponseEntity<>(latv, HttpStatus.OK);
	}

    @GetMapping(value = "/atividades/{titulo}")
	@ApiOperation(value = "Método que busca uma atividade as atividades do PET.")
	public ResponseEntity<?> buscarAtividadesTitulo(@ApiParam(value = "Titulo da atividade a ser solicitada") @PathVariable String titulo){
    	Atividades atv = atividadesService.buscarPorTitulo(titulo);
		if (atv == null) {
			throw new ResourceNotFoundException("Nenhuma atividade encontrada");
		}
		return new ResponseEntity<>(atv, HttpStatus.OK);
	}
    
    @PostMapping(value = "/atividades-cadastrar")
	@ApiOperation(value = "Método que cadastra as atividades do PET.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    @Secured({"ROLE_tutor", "ROLE_petiano"})
    public ResponseEntity<?> cadastrarAtividades(@Valid @RequestBody Atividades atv){
    	atividadesService.salvar(atv);

		return new ResponseEntity<>(HttpStatus.OK);
    }

}