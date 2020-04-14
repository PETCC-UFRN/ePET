package br.ufrn.ePET.controller;

import br.ufrn.ePET.models.Informacoes;
import br.ufrn.ePET.models.Noticia;
import br.ufrn.ePET.service.InformacoesService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class InformacoesController {

    private final InformacoesService informacoesService;

    @Autowired
    public InformacoesController(InformacoesService informacoesService) {
        this.informacoesService = informacoesService;
    }

    @GetMapping(value = "/informacoes")
    public ResponseEntity<?> buscarInformacoes(){
        return new ResponseEntity<>(informacoesService.buscar(), HttpStatus.OK);
    }

    @PostMapping(value = "/informacoes-cadastro")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    @Secured({"ROLE_tutor", "ROLE_petiano"})
    public ResponseEntity<?> salvarInformacoes(@RequestBody Informacoes informacoes){
        informacoesService.salvar(informacoes);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
