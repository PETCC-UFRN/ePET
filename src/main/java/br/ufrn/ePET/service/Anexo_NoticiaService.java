package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Noticia;
import br.ufrn.ePET.models.Noticia;
import br.ufrn.ePET.repository.Anexo_NoticiaRepository;
import br.ufrn.ePET.repository.NoticiaRepository;

@Service
public class Anexo_NoticiaService {

	private final Anexo_NoticiaRepository anexo_NoticiaRepository;
	private final NoticiaRepository noticiaRepository;
	
	@Autowired
	public Anexo_NoticiaService(Anexo_NoticiaRepository anexo_NoticiaRepository, NoticiaRepository noticiaRepository) {
		this.anexo_NoticiaRepository = anexo_NoticiaRepository;
		this.noticiaRepository = noticiaRepository;
	}
	
	public List<Anexo_Noticia> buscarPorNoticia(Long id_noticia){
		return anexo_NoticiaRepository.findById_noticia(id_noticia);
	}

	public Anexo_Noticia salvar(Long id_noticia, Anexo_Noticia anexo_Noticia) {
		Noticia n = noticiaRepository.findById(id_noticia).isPresent() ?
				noticiaRepository.findById(id_noticia).get() : null;
		if(n == null) {
			throw new ResourceNotFoundException("Noticia com id " + id_noticia + " não encontrada");
		}
		anexo_Noticia.setNoticia(n);
		return anexo_NoticiaRepository.save(anexo_Noticia);
 	}
	
	public void remover(Long id) {
		if(!anexo_NoticiaRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Anexo noticia com id " + id +  "não encontrado");
		}
		anexo_NoticiaRepository.deleteByIdNoticia(id);
	}
}
