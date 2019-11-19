package br.ufrn.ePET.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Tutoria;
import br.ufrn.ePET.models.TutoriaPadrao;
import br.ufrn.ePET.repository.DisciplinaRepository;
import br.ufrn.ePET.repository.PetianoRepository;
import br.ufrn.ePET.repository.TutoriaRepository;

@Service
public class TutoriaService extends TutoriaPadrao{
	
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
	
	public Page<Tutoria> buscar(Pageable pageable){
		return tutoriaRepository.findAll(pageable);
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
	
	public void remover(Long id) {
		if(!tutoriaRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Nenhuma tutoria com id " + id + "encontrada");
		}
		tutoriaRepository.deleteById(id);
	}

	@Override
	public void marcarTutoria(Long id_petiano, Long id_disciplina, Tutoria tutoria) {
		salvar(id_petiano, id_disciplina, tutoria);
		/*Aqui será implementado um método para envio de emails*/
	}
}
