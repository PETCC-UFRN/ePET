package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Organizadores;
import br.ufrn.ePET.repository.OrganizadoresRepository;

@Service
public class OrganizadoresService {
	
	private final OrganizadoresRepository organizadoresRepository;
	
	@Autowired
	public OrganizadoresService(OrganizadoresRepository organizadoresRepository) {
		this.organizadoresRepository = organizadoresRepository;
	}
	
	public List<Organizadores> buscar(){
		return organizadoresRepository.findAll();
	}
	
	public Organizadores buscar(Long id) {
		return organizadoresRepository.findById(id).get();
	}
	
	public void salvar(Organizadores organizadores) {
		organizadoresRepository.save(organizadores);
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
