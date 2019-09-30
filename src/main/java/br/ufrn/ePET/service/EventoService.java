package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.repository.EventoRepository;

@Service
public class EventoService {
	
	private final EventoRepository eventoRepository;
	
	@Autowired
	public EventoService(EventoRepository eventoRepository) {
		this.eventoRepository = eventoRepository;
	}
	
	public Evento buscar(Long id) {
		return eventoRepository.findById(id).isPresent() ? 
				eventoRepository.findById(id).get(): null;
	}
	
	public Page<Evento> buscar(Pageable pageable){
		Page<Evento> eventList = eventoRepository.findAll(pageable);
		if (eventList.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento cadastrado");
		return eventList;
	}
	
	public Page<Evento> buscarAtivos(Pageable pageable){
		/*Page<Evento> lista = eventoRepository.findAll(pageable);
		List<Evento> lista_aux = eventoRepository.findAll();
		LocalDate ld = LocalDate.now();
		for(Evento e : lista_aux) {
			if(!(ld.compareTo(e.getD_inscricao()) >= 0) && !(ld.compareTo(e.getD_inscricao_fim()) >= 0)) {
				lista_aux.remove(e);
			}
		}
		//lista_aux.clear();*/
		Page<Evento> lista = eventoRepository.findByAtivos(LocalDate.now(), pageable);
		if(lista.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento ativo");
		return lista;
	}
	
	public List<Evento> buscarInativos(){
		List<Evento> lista = eventoRepository.findByInativos();
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum evento inativo");
		}
		return lista;
	}

	public Evento salvar(Evento evento) {
		return eventoRepository.save(evento);
	}
	
	public void remover(Long id) {
		if (!eventoRepository.findById(id).isPresent())
			throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		eventoRepository.deleteById(id);
	}
	
	public void ativar(Long id) {
		Evento evento = new Evento();
		if (eventoRepository.findById(id).isPresent())
			evento = eventoRepository.findById(id).get();
		else throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		
		//Evento evento = eventoRepository.findById(id).get();
		evento.setAtivo(true);
		eventoRepository.save(evento);
	}
}
