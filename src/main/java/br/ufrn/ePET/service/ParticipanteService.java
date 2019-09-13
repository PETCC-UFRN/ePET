package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.repository.ParticipanteRepository;

@Service
public class ParticipanteService {
	
	private final ParticipanteRepository participanteRepository;
	
	@Autowired
	public ParticipanteService(ParticipanteRepository participanteRepository) {
		this.participanteRepository = participanteRepository;
	}
	
	public List<Participante> buscar(){
		return participanteRepository.findAll();
	}
	
	public Participante buscar(Long id) {
		return participanteRepository.findById(id).get();
	}
	
	public List<Participante> buscarPessoa(Long id){
		return participanteRepository.findByPessoa(id);
	}
	
	public List<Participante> buscarEventosAtuais(Long id){
		List<Participante> lista = participanteRepository.findByPessoa(id);
		for(Participante p : lista) {
			//if(p.getEvento().)
		}
		return lista;
	}
	
	public void salvar(Participante participante) {
		int ativos =  participanteRepository.countAtivos(participante.getEvento().getIdEvento());
		if(!(ativos < participante.getEvento().getQtdVagas())) {
			participante.setEspera(true);
		}
		participanteRepository.save(participante);
	}
	
	public void remover(Long id) {
		Participante p = participanteRepository.findById(id).get();
		if(p.isEspera()) {
			participanteRepository.delete(p);
		} else {
			List<Participante> lista = participanteRepository.findByEspera(true);
			if(lista.size() > 0) {
				lista.get(0).setEspera(false);
				participanteRepository.save(lista.get(0));
			}
		}
		
	}
	
}
