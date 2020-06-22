package br.ufrn.ePET.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Frequencia;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.models.Periodo_Evento;
import br.ufrn.ePET.repository.FrequenciaRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;
import br.ufrn.ePET.repository.Periodo_EventoReposioty;

@Service
public class FrequenciaService {

	private final FrequenciaRepository frequenciaRepository;
	private final Periodo_EventoReposioty peRepository;
	private final ParticipanteRepository participanteRepository;
	
	@Autowired
	public FrequenciaService(FrequenciaRepository frequenciaRepository, Periodo_EventoReposioty peRepository,
			ParticipanteRepository participanteRepository) {
		this.frequenciaRepository = frequenciaRepository;
		this.peRepository = peRepository;
		this.participanteRepository = participanteRepository;
	}
	
	public Page<Frequencia> buscar(Pageable pageable) {
		return frequenciaRepository.findAll(pageable);
	}

	public Frequencia buscar(Long id) {
		return frequenciaRepository.findById(id).isPresent() ? 
				frequenciaRepository.findById(id).get() : null;
	}
	
	public int buscarPorParticipante(Long id, Long id_evento) {
		return frequenciaRepository.findAssiduidadeByParticipante(id, id_evento);
	}
	
	public Page<Frequencia> buscarPorEvento(Long id_evento, Pageable pageable) {
		return frequenciaRepository.findByEventoID(id_evento, pageable);
	}
	
	public void salvar(Long id_periodo_evento, Long id_participante, Frequencia f) {
		Periodo_Evento pe = peRepository.findById(id_periodo_evento).isPresent() ? 
				peRepository.findById(id_periodo_evento).get() : null;
		Participante pa = participanteRepository.findById(id_participante).isPresent() ?
				participanteRepository.findById(id_participante).get(): null;
		if(pe == null)
			throw new ResourceNotFoundException("Periodo evento com id "+ id_periodo_evento + " não encontrado.");
		if(pa == null)
			throw new ResourceNotFoundException("Participante com id "+ id_participante + " não encontrado.");
		Frequencia freq = frequenciaRepository.findByPeriodoAndParticipante(id_periodo_evento, id_participante);
		if(freq != null) {
			f.setIdFrequencia(freq.getIdFrequencia());
		}
		f.setParticipante(pa);
		f.setPeriodo_evento(pe);
		frequenciaRepository.save(f);
	}
	
	public void remover(Long id) {
		if(!frequenciaRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Frequencia com id "+ id + " não encontrada.");
		}
		frequenciaRepository.deleteById(id);
	}
	
}
