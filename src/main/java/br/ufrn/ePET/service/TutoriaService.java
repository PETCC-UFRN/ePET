package br.ufrn.ePET.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.TutoriaOnline;
import br.ufrn.ePET.repository.DisciplinaRepository;
import br.ufrn.ePET.repository.PetianoRepository;
import br.ufrn.ePET.repository.TutoriaOnlineRepository;

@Service
public class TutoriaService{
	
	private final TutoriaOnlineRepository tutoriaOnlineRepository;
	private final PetianoRepository petianoRepository;
	private final DisciplinaRepository disciplinaRepository;
	
	@Autowired
	public TutoriaService( TutoriaOnlineRepository tutoriaRepository, PetianoRepository petianoRepository,
						   DisciplinaRepository disciplinaRepository) {
		this.tutoriaOnlineRepository = tutoriaRepository;
		this.petianoRepository = petianoRepository;
		this.disciplinaRepository = disciplinaRepository;
	}
	
	public TutoriaOnline buscar(Long id) {
		return tutoriaOnlineRepository.findById(id).get();
	}
	
	public Page<TutoriaOnline> buscar(Pageable pageable){
		return tutoriaOnlineRepository.findAllNota(pageable);
	}
	
	public TutoriaOnline salvar(Long id_petiano, Long id_disciplina, TutoriaOnline tutoriaOnline) {
		if(this.petianoRepository.findById(id_petiano).isPresent())
			tutoriaOnline.setPetiano(this.petianoRepository.findById(id_petiano).get());
		else
			throw new ResourceNotFoundException("Nenhum petiano cadastrado com o id: " + id_petiano);
		
		if(this.disciplinaRepository.findById(id_disciplina).isPresent())
			tutoriaOnline.setDisciplina(this.disciplinaRepository.findById(id_disciplina).get());
		else
			throw new ResourceNotFoundException("Nenhuma disciplina cadastrada com o id: " + id_disciplina);
		
		tutoriaOnline.setAvaliacoes(0);
		return tutoriaOnlineRepository.save(tutoriaOnline);
	}

	public TutoriaOnline avaliar(TutoriaOnline tutoriaOnline, Double nota) {
		tutoriaOnline.setAvaliacoes(tutoriaOnline.getAvaliacoes()+1);
		Double n = tutoriaOnline.getNota();
		if(n.isNaN())
			n = 0.0;
		tutoriaOnline.setNota((n+nota)/tutoriaOnline.getAvaliacoes());
		
		return tutoriaOnlineRepository.save(tutoriaOnline);
	}
	
	public void remover(Long id) {
		if(!tutoriaOnlineRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Nenhuma tutoria com id " + id + "encontrada");
		}
		tutoriaOnlineRepository.deleteById(id);
	}
}
