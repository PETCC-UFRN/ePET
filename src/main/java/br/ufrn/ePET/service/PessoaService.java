package br.ufrn.ePET.service;

import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.PessoaRepository;

@Service
public class PessoaService {
	
	private PessoaRepository pessoaRepository;
	
	public PessoaService(PessoaRepository p) {
		pessoaRepository = p;
	}
	
	public void save(Pessoa p) {
		
		this.pessoaRepository.save(p);
	}
	
	public Pessoa findByNome(String nome) {
		return pessoaRepository.findByNome(nome);
	}
	
	public void deletePessoa(Integer id) {
		this.pessoaRepository.deleteById(id);
	}
	

}
