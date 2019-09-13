package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufrn.ePET.models.Organizadores;
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
		return organizadoresRepository.findById(id).get();
	}
	
	public void salvar(Long id_evento, Long id_pessoa) {
		Organizadores o = new Organizadores();
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
		organizadoresRepository.deleteById(id);
	}
}
