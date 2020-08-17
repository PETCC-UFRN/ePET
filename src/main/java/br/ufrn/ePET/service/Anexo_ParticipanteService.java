package br.ufrn.ePET.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.CustomException;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Anexo_Participante;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.Anexo_ParticipanteRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;

@Service
public class Anexo_ParticipanteService {
	
	private final Anexo_ParticipanteRepository anexo_ParticipanteRepository;
	private final ParticipanteRepository participanteRepository;
	private final PessoaService pessoaService;
	
	@Autowired
	public Anexo_ParticipanteService(Anexo_ParticipanteRepository anexo_NoticiaRepository, ParticipanteRepository participanteRepository, PessoaService pessoaService) {
		this.anexo_ParticipanteRepository = anexo_NoticiaRepository;
		this.participanteRepository = participanteRepository;
		this.pessoaService = pessoaService;
	}
	
	public List<Anexo_Participante> buscarPorParticipante(Long id_participante){
		return anexo_ParticipanteRepository.findByIdParticipante(id_participante);
	}
	
	public Anexo_Participante salvar(Long id_participante, Anexo_Participante anexo_Participante) {
		Participante n = participanteRepository.findById(id_participante).isPresent() ?
				participanteRepository.findById(id_participante).get() : null;
		if(n == null) {
			throw new ResourceNotFoundException("Participante com id " + id_participante + " não encontrado");
		}
		anexo_Participante.setParticipante(n);
		return anexo_ParticipanteRepository.save(anexo_Participante);
 	}

	public Anexo_Participante salvar(Long id_participante, Anexo_Participante anexo_Participante, HttpServletRequest req) {
		Participante n = participanteRepository.findById(id_participante).isPresent() ?
				participanteRepository.findById(id_participante).get() : null;
		if(n == null) {
			throw new ResourceNotFoundException("Participante com id " + id_participante + " não encontrado");
		}
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p !=  n.getPessoa())
			throw new CustomException("Você não tem permissão para anexar!", HttpStatus.FORBIDDEN); 
		anexo_Participante.setParticipante(n);
		return anexo_ParticipanteRepository.save(anexo_Participante);
 	}
	
	public void remover(Long id, HttpServletRequest req) {
		if(!anexo_ParticipanteRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Anexo participante com id " + id +  "não encontrado");
		}
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p.getTipo_usuario().getNome() == "comum" && p != anexo_ParticipanteRepository.findById(id).get().getParticipante().getPessoa())
			throw new CustomException("Você não tem permissão para anexar!", HttpStatus.FORBIDDEN); 
		anexo_ParticipanteRepository.deleteByIdParticipante(id);
	}
	
}
