package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.CustomException;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.EventoDTO;
import br.ufrn.ePET.models.Organizadores;
import br.ufrn.ePET.models.Periodo_Evento;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.EventoRepository;

@Service
@Transactional
public class EventoService {
	
	private final EventoRepository eventoRepository;
	private final OrganizadoresService organizadoresService;
	private final PessoaService pessoaService;
	private final Periodo_EventoService periodoEventoService;
	
	@Autowired
	public EventoService(EventoRepository eventoRepository, OrganizadoresService organizadorService, PessoaService pessoaService, Periodo_EventoService periodoEventoService) {
		this.eventoRepository = eventoRepository;
		this.organizadoresService = organizadorService;
		this.pessoaService = pessoaService;
		this.periodoEventoService = periodoEventoService;
	}
	
	public EventoDTO buscar(Long id) {
		Evento e = new Evento();
		if (eventoRepository.findById(id).isPresent())
			e = eventoRepository.findById(id).get();
		else
			throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		EventoDTO eventodto = new EventoDTO();
		eventodto.setAtivo(e.isAtivo());
		eventodto.setD_inscricao(e.getD_inscricao());
		eventodto.setD_inscricao_fim(e.getD_inscricao_fim());
		eventodto.setDescricao(e.getDescricao());
		eventodto.setDias_compensacao(e.getDias_compensacao());
		eventodto.setFim_rolagem(e.getFim_rolagem());
		eventodto.setIdEvento(e.getIdEvento());
		eventodto.setImagem(e.getImagem());
		eventodto.setInicio_rolagem(e.getInicio_rolagem());
		eventodto.setLocal(e.getLocal());
		eventodto.setParticipante_anexos(e.isParticipante_anexos());
		eventodto.setPercentual(e.getPercentual());
		eventodto.setQtdCargaHoraria(e.getQtdCargaHoraria());
		List<Periodo_Evento> list = periodoEventoService.buscarPorEvento(e);
		ArrayList<LocalDate> arrayList = new ArrayList<LocalDate>();
		for (Periodo_Evento data : list) {
			arrayList.add(data.getDia());
		}
		eventodto.setPeriodo_evento(arrayList);
		eventodto.setQtdVagas(e.getQtdVagas());
		eventodto.setTextoDeclaracaoEvento(e.getTextoDeclaracaoEvento());
		eventodto.setTextoDeclaracaoEventoOrganizador(e.getTextoDeclaracaoEventoOrganizador());
		eventodto.setTitulo(e.getTitulo());
		eventodto.setValor(e.getValor());
		
		return eventodto;
	}
	
	public Page<Evento> buscar(Pageable pageable){
		Page<Evento> eventList = eventoRepository.findEventos(pageable);
		/*if (eventList.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento cadastrado");*/
		return eventList;
	}
	
	public List<Evento> buscarAtivos(){
		List<Evento> lista_aux = eventoRepository.findByAtivos();
		LocalDate ld = LocalDate.now();
		List<Evento> aux = new ArrayList<Evento>();
		for(Evento e : lista_aux) {
			if(!(ld.compareTo(e.getD_inscricao()) < 0) && !(ld.compareTo(e.getD_inscricao_fim()) > 0) ) {
				//System.out.println(e.getTitulo());
				aux.add(e);
			}
		}
		//lista_aux.clear();
		/*java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
		Page<Evento> lista = eventoRepository.findByAtivos(sqlDate, pageable);
		System.out.println(LocalDate.now());*/
		if(lista_aux.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento aberto para a inscrição");
		return aux;
	}

	public Page<Evento> buscarPorTitulo(String titulo, Pageable pageable){
		return eventoRepository.findByTitulo(titulo, pageable);
	}
	
	public List<Evento> buscarInativos(){
		List<Evento> lista = eventoRepository.findByInativos();
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum evento inativo");
		}
		return lista;
	}

	public Evento salvar(EventoDTO eventodto, HttpServletRequest req) {
		Pessoa p = pessoaService.buscarPorEmail(req);
		if(eventodto.getIdEvento() <= 0 && p.getTipo_usuario().getNome() == "comum")
			throw new CustomException("Você não tem permissão para criar um evento", HttpStatus.FORBIDDEN);
		if(eventodto.getIdEvento() > 0 && (p.getTipo_usuario().getNome() == "petiano" || p.getTipo_usuario().getNome() == "comum")) {
    		List<Organizadores>o = organizadoresService.buscarPessoa(p.getIdPessoa());
    		Boolean organiza = false;
    		for (Organizadores organizadores : o) {
    			if(organizadores.getEvento().getIdEvento() == eventodto.getIdEvento())
    			{
    				organiza = true;
    				break;
    			}
			}
			if (!organiza)
				throw new CustomException("Você não tem permissão para editar esse evento!", HttpStatus.FORBIDDEN);	
		}
		if(eventodto.getTextoDeclaracaoEvento() == null) {
			eventodto.setTextoDeclaracaoEvento("Certificamos que, para os devidos fins, que {nome_participante}, portador do CPF {cpf}, participou do evento {titulo_evento}, realizado no {local},"
	  		+ " nos dias {data_inicio} à {data_fim}, com uma carga-horária total de {carga_horária}h. Este evento foi promovido pelo Programa de "
	  		+ "Educação Tutorial do Curso de Ciência da Computação da Universidade Federal do Rio Grande do Norte (PET-CC/UFRN).");
		}
		if(eventodto.getTextoDeclaracaoEventoOrganizador() == null) {
			eventodto.setTextoDeclaracaoEventoOrganizador("Certificamos que, para os devidos fins, que {nome_participante}, portador do CPF {cpf}, participou da organização do evento {titulo_evento},"
	  		+ " com uma carga-horária total de {carga_horária} horas. Este evento ocorreu no {local}, no período de {data_inicio} à {data_fim}, sendo promovido pelo Programa de "
	  		+ "Educação Tutorial do Curso de Ciência da Computação da Universidade Federal do Rio Grande do Norte (PET-CC/UFRN).");
		}
		Evento e = new Evento();

		e.setIdEvento(eventodto.getIdEvento());
		e.setAtivo(eventodto.isAtivo());
		if (eventodto.getD_inscricao() != null)
			e.setD_inscricao(eventodto.getD_inscricao());
		if (eventodto.getD_inscricao_fim() != null)
			e.setD_inscricao_fim(eventodto.getD_inscricao_fim());
		if (eventodto.getDescricao() != null)
			e.setDescricao(eventodto.getDescricao());
		e.setDias_compensacao(eventodto.getDias_compensacao());
		if (eventodto.getFim_rolagem() != null)
			e.setFim_rolagem(eventodto.getFim_rolagem());
		if (eventodto.getImagem() != null)
			e.setImagem(eventodto.getImagem());
		if (eventodto.getInicio_rolagem() != null)
			e.setInicio_rolagem(eventodto.getInicio_rolagem());
		if (eventodto.getLocal() != null)
			e.setLocal(eventodto.getLocal());
		e.setParticipante_anexos(eventodto.isParticipante_anexos());
		e.setPercentual(eventodto.getPercentual());
		e.setQtdCargaHoraria(eventodto.getQtdCargaHoraria());
		if (eventodto.getPeriodo_evento() != null || !eventodto.getPeriodo_evento().isEmpty())
			e.setQtdDias(eventodto.getPeriodo_evento().size());
		e.setQtdVagas(eventodto.getQtdVagas());
		e.setTextoDeclaracaoEvento(eventodto.getTextoDeclaracaoEvento());
		e.setTextoDeclaracaoEventoOrganizador(eventodto.getTextoDeclaracaoEventoOrganizador());
		if (eventodto.getTitulo() != null)
			e.setTitulo(eventodto.getTitulo());
		e.setValor(eventodto.getValor());
		ArrayList<LocalDate> periodo_evento = eventodto.getPeriodo_evento();
		LocalDate evento_fim = periodo_evento.stream().max( Comparator.comparing( LocalDate::toEpochDay ) ).get();
		LocalDate evento_inicio = periodo_evento.stream().min( Comparator.comparing( LocalDate::toEpochDay ) ).get();
		e.setD_evento_fim(evento_fim);
		e.setD_evento_inicio(evento_inicio);
		eventoRepository.save(e);
		System.out.println("C");
		if (eventodto.getIdEvento() == 0 ) {
		organizadoresService.salvar(e.getIdEvento(), p.getIdPessoa());
		for (LocalDate dia : periodo_evento) {
			Periodo_Evento pe = new Periodo_Evento();
			pe.setDia(dia);
			periodoEventoService.salvar(e.getIdEvento(), pe);
		}
		}
		else {
			List<Periodo_Evento> lpe = periodoEventoService.buscarPorEvento(e);
			for (Periodo_Evento dia : lpe) {
				periodoEventoService.delete(dia.getIdPeriodo_Evento());
			}
			for (LocalDate dia : periodo_evento) {
				Periodo_Evento pe = new Periodo_Evento();
				pe.setDia(dia);
				periodoEventoService.salvar(e.getIdEvento(), pe);
			}
		}
		return e;
	}
	
	public void remover(Long id, HttpServletRequest req) {
    	Pessoa p = pessoaService.buscarPorEmail(req);
		if (!eventoRepository.findById(id).isPresent())
			throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		if(p.getTipo_usuario().getNome() == "petiano") {
			List<Organizadores>o = organizadoresService.buscarPessoa(p.getIdPessoa());
			Boolean organiza = false;
			for (Organizadores organizadores : o) {
				if(organizadores.getEvento().getIdEvento() == id)
				{
					organiza = true;
					break;
				}
			}
			if (!organiza)
	    		throw new CustomException("Você não tem permissão para apagar esse evento!", HttpStatus.FORBIDDEN);	
		}
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

	public Page<Evento> buscarNaoOrganizo(HttpServletRequest req, Pageable pageable){
		Pessoa p = pessoaService.buscarPorEmail(req);
		return eventoRepository.findEventosNaoOrganizo(p.getIdPessoa(), pageable);
	}

	public Page<Evento> buscarNaoOrganizoIna(HttpServletRequest req, Pageable pageable){
		Pessoa p = pessoaService.buscarPorEmail(req);
		return eventoRepository.findEventosNaoOrganizoIna(p.getIdPessoa(), pageable);
	}

	public EventoDTO buscarAtivosId(Long id){
		Evento e =  eventoRepository.findByAtivosId(id);
		if (e == null)
			throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		EventoDTO eventodto = new EventoDTO();
		eventodto.setAtivo(e.isAtivo());
		eventodto.setD_inscricao(e.getD_inscricao());
		eventodto.setD_inscricao_fim(e.getD_inscricao_fim());
		eventodto.setDescricao(e.getDescricao());
		eventodto.setDias_compensacao(e.getDias_compensacao());
		eventodto.setFim_rolagem(e.getFim_rolagem());
		eventodto.setIdEvento(e.getIdEvento());
		eventodto.setImagem(e.getImagem());
		eventodto.setInicio_rolagem(e.getInicio_rolagem());
		eventodto.setLocal(e.getLocal());
		eventodto.setParticipante_anexos(e.isParticipante_anexos());
		eventodto.setPercentual(e.getPercentual());
		eventodto.setQtdCargaHoraria(e.getQtdCargaHoraria());
		List<Periodo_Evento> list = periodoEventoService.buscarPorEvento(e);
		ArrayList<LocalDate> arrayList = new ArrayList<LocalDate>();
		for (Periodo_Evento data : list) {
			arrayList.add(data.getDia());
		}
		eventodto.setPeriodo_evento(arrayList);
		eventodto.setQtdVagas(e.getQtdVagas());
		eventodto.setTextoDeclaracaoEvento(e.getTextoDeclaracaoEvento());
		eventodto.setTextoDeclaracaoEventoOrganizador(e.getTextoDeclaracaoEventoOrganizador());
		eventodto.setTitulo(e.getTitulo());
		eventodto.setValor(e.getValor());
		
		return eventodto;
	}
}
