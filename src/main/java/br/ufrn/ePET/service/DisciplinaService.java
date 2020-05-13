package br.ufrn.ePET.service;

import java.util.List;
import java.util.Optional;

import br.ufrn.ePET.error.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Disciplina;
import br.ufrn.ePET.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

	private final DisciplinaRepository disciplinaRepository;
	
	@Autowired
	public DisciplinaService(DisciplinaRepository disciplinaRepository) {
		this.disciplinaRepository = disciplinaRepository;
	}
	
	public Disciplina buscar(Long id) {
		if(disciplinaRepository.findById(id).isPresent()) {
			return disciplinaRepository.findById(id).get();
		} else {
			throw new ResourceNotFoundException("Nenhuma disciplina encontrada");
		}
	}
	
	public Page<Disciplina> buscar(Pageable pageable){
		return disciplinaRepository.findAll(pageable);
	}

	public Page<Disciplina> buscarAtivos(Pageable pageable){
		return disciplinaRepository.findByAtivos(pageable);
	}

	public Page<Disciplina> buscarPorNomeOuCodigo(String search, Pageable pageable){
		return disciplinaRepository.findbyNomeOuCodigo(search, pageable);
	}

	public Page<Disciplina> buscarPorNomeOuCodigoAtivo(String search, Pageable pageable){
		return disciplinaRepository.findbyNomeOuCodigoAtivo(search, pageable);
	}
	
	public Disciplina salvar(Disciplina disciplina) {
		return disciplinaRepository.save(disciplina);
	}
	
	public void remover(Long id) {
		disciplinaRepository.deleteById(id);
	}
	
	
}
