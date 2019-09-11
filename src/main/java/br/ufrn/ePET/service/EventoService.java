package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return eventoRepository.findById(id).get();
	}
	
	public List<Evento> buscar(){
		return eventoRepository.findAll();
	}
	
	public List<Evento> buscarAtivos(){
		List<Evento> lista = eventoRepository.findAll();
		List<Evento> lista_ativos = null;
		LocalDate ld = LocalDate.now();
		for(Evento e : lista) {
			if(!(ld.compareTo(e.getD_inscricao()) >= 0) && !(ld.compareTo(e.getD_inscricao_fim()) >= 0)) {
				lista_ativos.add(e);
			}
		}
		return lista_ativos;
	}
	
	public Evento salvar(Evento evento) {
		return eventoRepository.save(evento);
	}
	
	public void remover(Long id) {
		eventoRepository.deleteById(id);
	}
	
	public void ativar(Long id) {
		Evento evento = eventoRepository.findById(id).get();
		evento.setAtivo(true);
		eventoRepository.save(evento);
	}
}
