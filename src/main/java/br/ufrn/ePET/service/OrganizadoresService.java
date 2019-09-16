package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Organizadores;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.EventoRepository;
import br.ufrn.ePET.repository.OrganizadoresRepository;
import br.ufrn.ePET.repository.PessoaRepository;

@Service
public class OrganizadoresService {
	
	private final OrganizadoresRepository organizadoresRepository;
	private final PessoaRepository pessoaRepository;
	private final EventoRepository eventoRepository;
	
	@Autowired
	public OrganizadoresService(OrganizadoresRepository organizadoresRepository, PessoaRepository pessoaRepository,
			EventoRepository eventoRepository) {
		this.organizadoresRepository = organizadoresRepository;
		this.pessoaRepository = pessoaRepository;
		this.eventoRepository = eventoRepository;
	}
	
	public List<Organizadores> buscar(){
		return organizadoresRepository.findAll();
	}

	public Organizadores buscar(Long id) {
		return organizadoresRepository.findById(id).isPresent() ? 
				organizadoresRepository.findById(id).get(): null;
	}
	
	public void salvar(Long id_evento, Long id_pessoa) {
		Organizadores o = new Organizadores();
		Evento evento = eventoRepository.findById(id_evento).isPresent() ? 
				eventoRepository.findById(id_evento).get(): null;
		Pessoa pessoa = pessoaRepository.findById(id_pessoa).isPresent() ? 
				pessoaRepository.findById(id_pessoa).get(): null;
		if(evento == null)
			throw new ResourceNotFoundException("Evento com id "+ id_evento + " não encontrado.");
		if(pessoa == null)
			throw new ResourceNotFoundException("Pessoa com id "+ id_evento + " não encontrada.");
		
		o.setEvento(eventoRepository.findById(id_evento).get());
		o.setPessoa(pessoaRepository.findById(id_pessoa).get());
		organizadoresRepository.save(o);
	}
	
	public List<Organizadores> buscarPessoa(Long id){
		return organizadoresRepository.findByPessoa(id);
	}
	
	public List<Organizadores> buscarEvento(Long id){
		return organizadoresRepository.findByEvento(id);
	}
	
	public void remover(Long id) {
		if (!organizadoresRepository.findById(id).isPresent())
			throw new ResourceNotFoundException("Nenhum organizador com id "+id+" encontrado");
		organizadoresRepository.deleteById(id);
	}
}
