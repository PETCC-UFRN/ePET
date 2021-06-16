package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Atividades;
import br.ufrn.ePET.repository.AtividadesRepository;

@Service
public class AtividadesService {

	private final AtividadesRepository atividadesRepository;

	@Autowired
	public AtividadesService(AtividadesRepository atividadesRepository) {
		super();
		this.atividadesRepository = atividadesRepository;
	}

	public Atividades buscarPorTitulo(String titulo) {
		return atividadesRepository.findByTitulo(titulo);
	}
	
	public List<Atividades> buscarTodos(){
		return atividadesRepository.findAll();
	}

	public void salvar(Atividades atv) {
		atividadesRepository.save(atv);
	}
	
	public void delete(Long id) {if(!atividadesRepository.findById(id).isPresent()) {
		throw new ResourceNotFoundException("Nenhuma atividade com id " + id + "encontrada");
	}
		atividadesRepository.deleteById(id);
	}

}
