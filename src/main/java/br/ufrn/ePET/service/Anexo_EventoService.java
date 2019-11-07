package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Evento;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.repository.Anexo_EventoRepository;
import br.ufrn.ePET.repository.EventoRepository;

@Service
public class Anexo_EventoService {
	
	private final Anexo_EventoRepository anexo_EventoRepository;
	private final EventoRepository eventoRepository;
	
	@Autowired
	public Anexo_EventoService(Anexo_EventoRepository anexo_EventoRepository, EventoRepository eventoRepository) {
		this.anexo_EventoRepository = anexo_EventoRepository;
		this.eventoRepository = eventoRepository;
	}
	
	public List<Anexo_Evento> buscarPorEvento(Long id_participante){
		return anexo_EventoRepository.findByIdEvento(id_participante);
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void salvar(Long id_evento, Anexo_Evento anexo_Evento) {
		Evento n = eventoRepository.findById(id_evento).isPresent() ?
				eventoRepository.findById(id_evento).get() : null;
		if(n == null) {
			throw new ResourceNotFoundException("Evento com id " + id_evento + " não encontrado");
		}
		anexo_Evento.setEvento(n);
		anexo_EventoRepository.save(anexo_Evento);
 	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void remover(Long id) {
		if(!anexo_EventoRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Anexo evento com id " + id +  "não encontrado");
		}
		anexo_EventoRepository.deleteByIdEvento(id);
	}
}
