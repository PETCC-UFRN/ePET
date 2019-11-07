package br.ufrn.ePET.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.ePET.models.Disciplina;
import br.ufrn.ePET.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

	private final DisciplinaRepository disciplinaRepository;
	
	@Autowired
	public DisciplinaService(DisciplinaRepository disciplinaRepository) {
		this.disciplinaRepository = disciplinaRepository;
	}
	
	public Optional<Disciplina> buscar(Long id) {
		return disciplinaRepository.findById(id);
	}
	
	public List<Disciplina> buscar(){
		return disciplinaRepository.findAll();
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Disciplina salvar(Disciplina disciplina) {
		return disciplinaRepository.save(disciplina);
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void remover(Long id) {
		disciplinaRepository.deleteById(id);
	}
	
	
}
