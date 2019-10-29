package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Participante;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.repository.Anexo_ParticipanteRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;

public class Anexo_ParticipanteService {
	
	private final Anexo_ParticipanteRepository anexo_ParticipanteRepository;
	private final ParticipanteRepository participanteRepository;
	
	@Autowired
	public Anexo_ParticipanteService(Anexo_ParticipanteRepository anexo_NoticiaRepository, ParticipanteRepository participanteRepository) {
		this.anexo_ParticipanteRepository = anexo_NoticiaRepository;
		this.participanteRepository = participanteRepository;
	}
	
	public List<Anexo_Participante> buscarPorParticipante(Long id_participante){
		return anexo_ParticipanteRepository.findByIdParticipante(id_participante);
	}

	public void salvar(Long id_participante, Anexo_Participante anexo_Participante) {
		Participante n = participanteRepository.findById(id_participante).isPresent() ?
				participanteRepository.findById(id_participante).get() : null;
		if(n == null) {
			throw new ResourceNotFoundException("Participante com id " + id_participante + " não encontrado");
		}
		anexo_Participante.setParticipante(n);
		anexo_ParticipanteRepository.save(anexo_Participante);
 	}
	
	public void remover(Long id) {
		if(!anexo_ParticipanteRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Anexo participante com id " + id +  "não encontrado");
		}
		anexo_ParticipanteRepository.deleteByIdParticipante(id);
	}
	
}
