package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Frequencia;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.models.Periodo_Evento;
import br.ufrn.ePET.models.Seminario;
import br.ufrn.ePET.repository.EventoRepository;
import br.ufrn.ePET.repository.FrequenciaRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;
import br.ufrn.ePET.repository.Periodo_EventoReposioty;

@Service
public class EventoService extends Seminario{
	
	private final EventoRepository eventoRepository;
	private final FrequenciaRepository frequenciaRepository;
	private final Periodo_EventoReposioty periodo_EventoRepository;
	private final ParticipanteRepository participanteRepository;
	
	@Autowired
	public EventoService(EventoRepository eventoRepository, FrequenciaRepository frequenciaRepository
			, Periodo_EventoReposioty periodo_EventoRepository, ParticipanteRepository participanteRepository) {
		this.eventoRepository = eventoRepository;
		this.frequenciaRepository = frequenciaRepository;
		this.periodo_EventoRepository = periodo_EventoRepository;
		this.participanteRepository = participanteRepository;
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
	
	public Page<Evento> ordenar(){
		//Método que vai retornar ordenado de acordo com a aplicação
		
		List<Evento> eventos = eventoRepository.findAll();
		
		return null;
	}
	
	public List<Evento> buscarAtivos(Pageable pageable){
		//Page<Evento> lista = eventoRepository.findAll(pageable);
		List<Evento> lista_aux = eventoRepository.findAll();
		LocalDate ld = LocalDate.now();
		for(Evento e : lista_aux) {
			if(!(ld.compareTo(e.getD_inscricao()) >= 0) && !(ld.compareTo(e.getD_inscricao_fim()) >= 0)) {
				lista_aux.remove(e);
			}
		}
		//lista_aux.clear();
		/*java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
		Page<Evento> lista = eventoRepository.findByAtivos(sqlDate, pageable);
		System.out.println(LocalDate.now());
		if(lista.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento ativo");*/
		return lista_aux;
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
		Evento evento;
		if (eventoRepository.findById(id).isPresent())
			evento = eventoRepository.findById(id).get();
		else throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		
		//Evento evento = eventoRepository.findById(id).get();
		evento.setAtivo(true);
		eventoRepository.save(evento);
	}
	
	@Override
	public void realizarFrequencia(Long id_periodo, Long id_participante, int presencas) {
		Periodo_Evento pe;
		if(periodo_EventoRepository.findById(id_periodo).isPresent())
			pe = periodo_EventoRepository.findById(id_periodo).get();
		else throw new ResourceNotFoundException("Nenhum periodo de evento com id "+id_periodo+" encontrado.");
		
		Frequencia f = new Frequencia();
		Participante p;
		if(participanteRepository.findById(id_participante).isPresent())
			p = participanteRepository.findById(id_participante).get();
		else throw new ResourceNotFoundException("Nenhum participante com id "+id_participante+" encontrado");
		
		int assiduidade = 100 - (pe.getEvento().getQtdCargaHoraria() - presencas);
		f.setAssiduidade(assiduidade);
		f.setParticipante(p);
		f.setPeriodo_evento(pe);
		frequenciaRepository.save(f);
	}
}
