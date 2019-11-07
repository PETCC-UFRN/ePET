package br.ufrn.ePET.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Tutoria;
import br.ufrn.ePET.repository.DisciplinaRepository;
import br.ufrn.ePET.repository.PetianoRepository;
import br.ufrn.ePET.repository.TutoriaRepository;
import br.ufrn.ePET.repository.Tutoria_Ministrada_Repository;

@Service
public class TutoriaService {
	
	private final TutoriaRepository tutoriaRepository;
	private final PetianoRepository petianoRepository;
	private final DisciplinaRepository disciplinaRepository;
	private final Tutoria_Ministrada_Repository tutoriaMinistrada_Repository;
	
	
	public TutoriaService( TutoriaRepository tutoriaRepository, PetianoRepository petianoRepository,
						   DisciplinaRepository disciplinaRepository, Tutoria_Ministrada_Repository tutoriaMinistrada_Repository) {
		this.tutoriaRepository = tutoriaRepository;
		this.petianoRepository = petianoRepository;
		this.disciplinaRepository = disciplinaRepository;
		this.tutoriaMinistrada_Repository = tutoriaMinistrada_Repository;
	}
	
	public Tutoria buscar(Long id) {
		return tutoriaRepository.findById(id).get();
	}
	
	public Page<Tutoria> buscar(Pageable pageable){
		return tutoriaRepository.findByAtivos(pageable);
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Tutoria salvar(Long id_petiano, Long id_disciplina, Tutoria tutoria) {
		if(this.petianoRepository.findById(id_petiano).isPresent())
			tutoria.setPetiano(this.petianoRepository.findById(id_petiano).get());
		else
			throw new ResourceNotFoundException("Nenhum petiano cadastrado com o id: " + id_petiano);
		
		if(this.disciplinaRepository.findById(id_disciplina).isPresent())
			tutoria.setDisciplina(this.disciplinaRepository.findById(id_disciplina).get());
		else
			throw new ResourceNotFoundException("Nenhuma disciplina cadastrada com o id: " + id_disciplina);
		
		
		return tutoriaRepository.save(tutoria);
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void remover(Long id) {
		Tutoria t = tutoriaRepository.findById(id).get();
		if(t == null) {
			throw new ResourceNotFoundException("Nenhuma tutoria com id " + id + "encontrada");
		}
		tutoriaMinistrada_Repository.desativarAtivos(id);
		t.setAtivo(false);
		tutoriaRepository.save(t);
	}
}
