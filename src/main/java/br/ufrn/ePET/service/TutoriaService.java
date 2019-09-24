package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Tutoria;
import br.ufrn.ePET.repository.DisciplinaRepository;
import br.ufrn.ePET.repository.PetianoRepository;
import br.ufrn.ePET.repository.TutoriaRepository;

@Service
public class TutoriaService {
	
	private final TutoriaRepository tutoriaRepository;
	private final PetianoRepository petianoRepository;
	private final DisciplinaRepository disciplinaRepository;
	
	public TutoriaService( TutoriaRepository tutoriaRepository, PetianoRepository petianoRepository,
						   DisciplinaRepository disciplinaRepository) {
		this.tutoriaRepository = tutoriaRepository;
		this.petianoRepository = petianoRepository;
		this.disciplinaRepository = disciplinaRepository;
	}
	
	public Tutoria buscar(Long id) {
		return tutoriaRepository.findById(id).get();
	}
	
	public List<Tutoria> buscar(){
		return tutoriaRepository.findAll();
	}
	
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

}
