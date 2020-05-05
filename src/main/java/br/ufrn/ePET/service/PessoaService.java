package br.ufrn.ePET.service;

import br.ufrn.ePET.security.JwtTokenProvider;
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

import javax.servlet.http.HttpServletRequest;


@Service
public class PessoaService {
	
	private final PessoaRepository pessoaRepository;
	private final Tipo_UsuarioRepository tipo_UsuarioRespository;
	private final UsuarioRepository usuarioRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
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
	
	public Pessoa buscarPorEmail(HttpServletRequest req) {
		Usuario u = usuarioRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
		Pessoa p = pessoaRepository.findByUsuario(u);
		return p;
	}

	public Page<Pessoa> buscarPorNomeOrCpf(String search, Pageable pageable){
		return pessoaRepository.findByNomeOrCPF(search, pageable);
	}

	public Page<Pessoa> buscar(Pageable pageable){
		return pessoaRepository.findAll(pageable);
	}
	
	public Pessoa salvar(Long id_tipo, Long id_usuario, Pessoa pessoa, int control){

		if(!tipo_UsuarioRespository.findById(id_tipo).isPresent())
			throw new ResourceNotFoundException("Nenhum tipo de usuário cadastrado com id "+ id_tipo);

		if(!usuarioRepository.findById(id_usuario).isPresent())
			throw new ResourceNotFoundException("Nenhum usuário cadastrado com id "+ id_usuario);

		if(control == 0 || control == 1){
			pessoa.setTipo_usuario(tipo_UsuarioRespository.findById(id_tipo).get());
			pessoa.setUsuario(usuarioRepository.findById(id_usuario).get());
			return pessoaRepository.save(pessoa);
		} else {
			Pessoa p = pessoaRepository.findById(pessoa.getIdPessoa()).get();
			if(p.getTipo_usuario().getNome().equalsIgnoreCase("comum")){
				if(p != null){
					p.setCpf(pessoa.getCpf());
					p.setTipo_usuario(tipo_UsuarioRespository.findById(Long.valueOf(2)).get());
					p.setNome(pessoa.getNome());
					return pessoaRepository.save(p);
				} else {
					throw new ResourceNotFoundException("Nenhuma pessoa encontrado!");
				}
			} else {
				throw new RuntimeException("Tentativa de mudança de uma pessoa não autorizada!");
			}
		}
	}
}
