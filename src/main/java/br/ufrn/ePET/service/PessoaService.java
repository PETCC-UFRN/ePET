package br.ufrn.ePET.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Usuario;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;
import br.ufrn.ePET.repository.UsuarioRepository;


@Service
public class PessoaService {
	
	private final PessoaRepository pessoaRepository;
	private final Tipo_UsuarioRepository tipo_UsuarioRespository;
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	public PessoaService(PessoaRepository pessoaRepository, Tipo_UsuarioRepository tipo_UsuarioRespository,
			UsuarioRepository usuarioRepository) {
		this.pessoaRepository = pessoaRepository;
		this.tipo_UsuarioRespository = tipo_UsuarioRespository;
		this.usuarioRepository = usuarioRepository;
	}
	
	public Pessoa buscar(Long id) {
		return pessoaRepository.findById(id).isPresent() ? 
				pessoaRepository.findById(id).get(): null;
	}
	
	public Pessoa buscarPorEmail(String email) {
		Usuario u = usuarioRepository.findByEmail(email);
		Pessoa p = pessoaRepository.findByUsuario(u);
		return p;
	}
	public Page<Pessoa> buscar(Pageable pageable){
		return pessoaRepository.findAll(pageable);
	}
	
	public Pessoa salvar(Long id_tipo, Long id_usuario, Pessoa pessoa){
		if(tipo_UsuarioRespository.findById(id_tipo).isPresent())
			pessoa.setTipo_usuario(tipo_UsuarioRespository.findById(id_tipo).get());
		else throw new ResourceNotFoundException("Nenhum tipo de usuário cadastrado com id "+ id_tipo);

		if(usuarioRepository.findById(id_usuario).isPresent())
			pessoa.setUsuario(usuarioRepository.findById(id_usuario).get());
		else throw new ResourceNotFoundException("Nenhum usuário cadastrado com id "+ id_usuario);
		
		return pessoaRepository.save(pessoa);
	}
}
