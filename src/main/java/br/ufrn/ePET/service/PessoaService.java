package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Tipo_Usuario;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;

@Service
public class PessoaService {
	
	private final PessoaRepository pessoaRepository;
	private final Tipo_UsuarioRepository tipo_UsuarioRespository;
	
	@Autowired
	public PessoaService(PessoaRepository pessoaRepository, Tipo_UsuarioRepository tipo_UsuarioRespository) {
		this.pessoaRepository = pessoaRepository;
		this.tipo_UsuarioRespository = tipo_UsuarioRespository;
	}
	
	public Pessoa buscar(Long id) {
		Pessoa pessoa = pessoaRepository.findById(id).get();
		return pessoa instanceof Pessoa ? pessoa : null;
	}
	
	public List<Pessoa> buscar(){
		return pessoaRepository.findAll();
	}
	
	public Pessoa salvar(Long id, Pessoa pessoa){
		Tipo_Usuario tu = tipo_UsuarioRespository.findById(id).get();
		pessoa.setTipo_usuario(tu);
		return pessoaRepository.save(pessoa);
		
	}
	
}
