package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.CustomException;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Organizadores;
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
	private final PessoaService pessoaService;
	private final OrganizadoresService organizadoresService;
	
	@Autowired
	public ParticipanteService(ParticipanteRepository participanteRepository, EventoRepository eventoRepository,
			PessoaRepository pessoaRepository, PessoaService pessoaService, OrganizadoresService organizadoresService) {
		this.participanteRepository = participanteRepository;
		this.eventoRepository = eventoRepository;
		this.pessoaRepository = pessoaRepository;
		this.pessoaService = pessoaService;
		this.organizadoresService = organizadoresService;
	}
	
	public Page<Participante> buscar(Pageable pageable){
		return participanteRepository.findAll(pageable);
	}
	
	public Participante buscar(HttpServletRequest req, Long id) {
		if (participanteRepository.findById(id).isPresent()) 
		{
			Participante part = participanteRepository.findById(id).get();
			Pessoa p = pessoaService.buscarPorEmail(req);
			if(p.getTipo_usuario().getNome() != "tutor") {
				List<Organizadores>o = organizadoresService.buscarPessoa(p.getIdPessoa());
				Boolean organiza = false;
				for (Organizadores organizador : o) {
					if(organizador.getEvento().getIdEvento() == part.getEvento().getIdEvento())
					{
						organiza = true;
						break;
					}
				}
				if (!organiza && (p.getTipo_usuario().getNome() == "comum" || p.getTipo_usuario().getNome() == "petiano"))
					throw new CustomException("Você não tem permissão para listar os participantes desse evento!", HttpStatus.FORBIDDEN);
			}
			return part;
		}
		else {
			throw new ResourceNotFoundException("Nenhum participante com id "+ id + " encontrado!");
		}
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
	public Page<Participante> buscarPorNomeOuCpfPessoa(String search, Pageable pageable){
		return participanteRepository.findByNomeOuCpfPessoa(search, pageable);
	}

	public Page<Participante> buscarPorTituloEvento(HttpServletRequest req, String search, Pageable pageable){
		Page<Participante> parts =  participanteRepository.findByTituloEvento(search, pageable);
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p.getTipo_usuario().getNome() != "tutor") {
			List<Organizadores>o = organizadoresService.buscarPessoa(p.getIdPessoa());
			Boolean organiza = false;
			for (Organizadores organizadores : o) {
				if(organizadores.getEvento().getIdEvento() == parts.getContent().get(0).getEvento().getIdEvento())
				{
					organiza = true;
					break;
				}
			}
			if (!organiza && (p.getTipo_usuario().getNome() == "comum" || p.getTipo_usuario().getNome() == "petiano"))
				throw new CustomException("Você não tem permissão para listar os participantes desse evento!", HttpStatus.FORBIDDEN);
		}		
		return parts;
		
	}

	public Page<Participante> buscarPorEvento(HttpServletRequest req, Long id, Pageable pageable){
		Page<Participante> page = participanteRepository.findByEvento(id, pageable);
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(p.getTipo_usuario().getNome() != "tutor") {
			List<Organizadores>o = organizadoresService.buscarPessoa(p.getIdPessoa());
			Boolean organiza = false;
			for (Organizadores organizadores : o) {
				if(organizadores.getEvento().getIdEvento() == id)
				{
					organiza = true;
					break;
				}
			}
			if (!organiza && (p.getTipo_usuario().getNome() == "comum" || p.getTipo_usuario().getNome() == "petiano"))
				throw new CustomException("Você não tem permissão para listar os participantes desse evento!", HttpStatus.FORBIDDEN);
		}
		return page;
	}

	public void salvar(Long id_evento, Long id_pessoa) {
		Participante p = new Participante();
		//Evento e = eventoRepository.findById(id_evento).get();
		//Pessoa pe = pessoaRepository.findById(id_pessoa).get();
		Evento e= eventoRepository.findById(id_evento).isPresent() ? 
				eventoRepository.findById(id_evento).get(): null;
		Pessoa pe= pessoaRepository.findById(id_pessoa).isPresent() ? 
				pessoaRepository.findById(id_pessoa).get(): null;

		if(participanteRepository.findByPessoaAndEvento(id_pessoa, id_evento) != null){
			throw new RuntimeException("Essa pessoa já fez sua inscrição no evento!");
		}
		if(e== null)
			throw new ResourceNotFoundException("Evento com id "+ id_evento + " não encontrado.");
		if(pe== null)
			throw new ResourceNotFoundException("Pessoa com id "+ id_evento + " não encontrada.");
		p.setEvento(e);
		p.setPessoa(pe);
		p.setData_maxima(LocalDate.now().plusDays(e.getDias_compensacao()));
		int ativos =  participanteRepository.countAtivos(e.getIdEvento());
		if(!(ativos < e.getQtdVagas())) {
			p.setEspera(true);
			p.setConfirmado(false);
		}
		else{
			p.setEspera(false);
			if (e.getValor() == 0.0) {
				p.setConfirmado(true);
			}
			else {
				p.setConfirmado(false);
			}
		}
		
		participanteRepository.save(p);
	}
	
	public Participante remover(Long id) {
		//Participante p = participanteRepository.findById(id).get();
		Participante p = participanteRepository.findById(id).isPresent() ? 
				participanteRepository.findById(id).get(): null;
		if(p == null)
			throw new ResourceNotFoundException("Participante com id "+ id + " não encontrado.");
		if(p.isEspera()) {
			participanteRepository.deleteById(p.getIdParticipantes());
		} else {
			participanteRepository.deleteById(p.getIdParticipantes());
			Participante lista = participanteRepository.findByEspera(p.getEvento().getIdEvento(), true);
			if(lista != null) {
				lista.setEspera(false);
				lista.setData_maxima(LocalDate.now().plusDays(lista.getEvento().getDias_compensacao()));
				participanteRepository.save(lista);
				return lista;
			}
		}
		return null;
	}
	
	public void confirmar(Long id) {
		Participante p = participanteRepository.findById(id).isPresent() ?
				participanteRepository.findById(id).get() : null;
		if(p == null) {
			throw new ResourceNotFoundException("Particiipante com id " + id + " não encontrado.");
		}
		p.setConfirmado(true);
		participanteRepository.save(p);
	}
	
}
