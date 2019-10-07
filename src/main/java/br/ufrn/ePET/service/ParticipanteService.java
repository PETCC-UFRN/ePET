package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.EventoRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;
import br.ufrn.ePET.repository.PessoaRepository;

@Service
public class ParticipanteService {
	
	private final ParticipanteRepository participanteRepository;
	private final EventoRepository eventoRepository;
	private final PessoaRepository pessoaRepository;
	
	@Autowired
	public ParticipanteService(ParticipanteRepository participanteRepository, EventoRepository eventoRepository,
			PessoaRepository pessoaRepository) {
		this.participanteRepository = participanteRepository;
		this.eventoRepository = eventoRepository;
		this.pessoaRepository = pessoaRepository;
	}
	
	public Page<Participante> buscar(Pageable pageable){
		return participanteRepository.findAll(pageable);
	}
	
	public Participante buscar(Long id) {
		return participanteRepository.findById(id).isPresent() ? 
				participanteRepository.findById(id).get(): null;
	}
	
	public Page<Participante> buscarPessoa(Long id, Pageable pageable){
		return participanteRepository.findByPessoa(id, pageable);
	}
	
	/*public List<Participante> buscarEventosAtuais(Long id){
		List<Participante> lista = participanteRepository.findByPessoa(id);
		for(Participante p : lista) {
			if(p.getEvento().get)
		}
		return lista;
	}*/
	
	public void salvar(Long id_evento, Long id_pessoa) {
		Participante p = new Participante();
		//Evento e = eventoRepository.findById(id_evento).get();
		//Pessoa pe = pessoaRepository.findById(id_pessoa).get();
		Evento e= eventoRepository.findById(id_evento).isPresent() ? 
				eventoRepository.findById(id_evento).get(): null;
		Pessoa pe= pessoaRepository.findById(id_pessoa).isPresent() ? 
				pessoaRepository.findById(id_pessoa).get(): null;
		if(e== null)
			throw new ResourceNotFoundException("Evento com id "+ id_evento + " não encontrado.");
		if(pe== null)
			throw new ResourceNotFoundException("Pessoa com id "+ id_evento + " não encontrada.");
		p.setEvento(e);
		p.setPessoa(pe);
		int ativos =  participanteRepository.countAtivos(e.getIdEvento());
		if(!(ativos < e.getQtdVagas())) {
			p.setEspera(true);
		}
		participanteRepository.save(p);
	}
	
	public void remover(Long id) {
		//Participante p = participanteRepository.findById(id).get();
		Participante p = participanteRepository.findById(id).isPresent() ? 
				participanteRepository.findById(id).get(): null;
		if(p == null)
			throw new ResourceNotFoundException("Participante com id "+ id + " não encontrado.");
		if(!p.isEspera()) {
			participanteRepository.deleteById(p.getIdParticipantes());
		} else {
			participanteRepository.deleteById(p.getIdParticipantes());
			List<Participante> lista = participanteRepository.findByEspera(true);
			if(lista.size() > 0) {
				lista.get(0).setEspera(false);
				participanteRepository.save(lista.get(0));
			}
		}
		
	}
	
}
