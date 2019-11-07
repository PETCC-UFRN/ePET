package br.ufrn.ePET.service;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Periodo_Evento;
import br.ufrn.ePET.repository.EventoRepository;
import br.ufrn.ePET.repository.Periodo_EventoReposioty;

@Service
public class Periodo_EventoService {
	
	private final Periodo_EventoReposioty periodo_EventoReposioty;
	private final EventoRepository eventoRepository;
	
	@Autowired
	public Periodo_EventoService(Periodo_EventoReposioty periodo_EventoReposioty, EventoRepository eventoRepository) {
		this.periodo_EventoReposioty = periodo_EventoReposioty;
		this.eventoRepository = eventoRepository;
	}
	
	public Periodo_Evento buscar(Long id) {
		return periodo_EventoReposioty.findById(id).isPresent() ? 
				periodo_EventoReposioty.findById(id).get() : null;
	}

	public Page<Periodo_Evento> buscar(Pageable pageable){
		return periodo_EventoReposioty.findAll(pageable);
	}
	
	public Page<Periodo_Evento> buscarPorEvento(Long id_evento, Pageable pageable){
		return periodo_EventoReposioty.findByEvento(id_evento, pageable);
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void salvar(Long id, Periodo_Evento pe) {
		Evento e = eventoRepository.findById(id).isPresent() ? 
				eventoRepository.findById(id).get() : null;
		if(e == null) {
			throw new ResourceNotFoundException("Evento com id "+ id + " não encontrado.");
		}
		pe.setEvento(e);
		periodo_EventoReposioty.save(pe);
	}
	
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void delete(Long id) {
		if(!periodo_EventoReposioty.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Nenhum periodo com id "+id+" encontrado");
		}
		periodo_EventoReposioty.deleteById(id);
	}
	
	
}
