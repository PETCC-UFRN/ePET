package br.ufrn.ePET.service;

import br.ufrn.ePET.models.Informacoes;
import br.ufrn.ePET.repository.InformacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformacoesService {

    private final InformacoesRepository informacoesRepository;

    @Autowired
    public InformacoesService(InformacoesRepository informacoesRepository) {
        this.informacoesRepository = informacoesRepository;
    }

    public Informacoes buscar() {
        return informacoesRepository.findInfo();
    }

    public void salvar(Informacoes informacoes){
        informacoesRepository.save(informacoes);
    }
}
