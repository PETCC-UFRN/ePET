package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Noticia;
import br.ufrn.ePET.models.Petiano;
import br.ufrn.ePET.repository.NoticiaRepository;
import br.ufrn.ePET.repository.PetianoRepository;

@Service
public class NoticiaService {
	
	private final NoticiaRepository noticiaRepository;
	private final PetianoRepository petianoRepository;
	
	@Autowired
	public NoticiaService(NoticiaRepository noticiaRepository, PetianoRepository petianoRepository) {
		this.noticiaRepository = noticiaRepository;
		this.petianoRepository = petianoRepository;
	}

	
	public Noticia buscar(Long id) {
		return noticiaRepository.findById(id).isPresent() ? 
				noticiaRepository.findById(id).get() : null;
	}
	
	public Page<Noticia> buscar(Pageable pageable){
		return noticiaRepository.findAll(pageable);
	}
	
	public void salvar(Long id_petiano, Noticia noticia) {
		Petiano petiano = petianoRepository.findById(id_petiano).isPresent() ?
				petianoRepository.findById(id_petiano).get() : null;
		if(petiano == null) {
			throw new ResourceNotFoundException("Nenhum petiano encontrado com id " + id_petiano + " encontrado");
		}
		noticia.setPetiano(petiano);
		noticiaRepository.save(noticia);
	}
	
	public void delete(Long id) {
		if(!noticiaRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Nenhuma notícia com id " + id + "encontrada");
		}
		noticiaRepository.deleteById(id);
	}
}
