package br.ufrn.ePET.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Noticia;
import br.ufrn.ePET.models.Anexo_Tutoria;
import br.ufrn.ePET.models.Noticia;
import br.ufrn.ePET.models.TutorialPresencial;
import br.ufrn.ePET.repository.Anexo_TutoriaRepository;
import br.ufrn.ePET.repository.TutoriaRepository;

@Service
public class Anexo_TutoriaService {

	private final Anexo_TutoriaRepository atr;
	private final TutoriaRepository tr;
	
	@Autowired
	public Anexo_TutoriaService(Anexo_TutoriaRepository a, TutoriaRepository t) {
		this.atr = a;
		this.tr = t;
	}
	
	public List<Anexo_Tutoria> buscarTodos(){
		return atr.findAll();
	}
	
	public List<Anexo_Tutoria> buscarPortutoria(Long id_tutoria){
		return atr.findById_tutoria(id_tutoria);
	}

	public void salvar(Long id_tutoria, Anexo_Tutoria anexo_tutoria) {
		TutorialPresencial n = tr.findById(id_tutoria).isPresent() ?
				tr.findById(id_tutoria).get() : null;
		if(n == null) {
			throw new ResourceNotFoundException("Tutoria com id " + id_tutoria + " não encontrada");
		}
		anexo_tutoria.setTutoria(n);
		atr.save(anexo_tutoria);
 	}
	
	public void remover(Long id) {
		if(!atr.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Anexo tutoria com id " + id +  "não encontrado");
		}
		atr.deleteByIdTutoria(id);
	}
	
	
}
