package br.ufrn.ePET.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.DuplicatedEntryException;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Tipo_Usuario;
import br.ufrn.ePET.models.Usuario;
import br.ufrn.ePET.models.UsuarioDTO;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;
import br.ufrn.ePET.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final Tipo_UsuarioRepository tipoUsuarioRepository;
	private final PessoaRepository pessoaRepository;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, Tipo_UsuarioRepository tipoUsuarioRepository,
			PessoaRepository pessoaRepository) {
		this.usuarioRepository = usuarioRepository;
		this.tipoUsuarioRepository = tipoUsuarioRepository;
		this.pessoaRepository = pessoaRepository;
	}
	
	public Usuario buscar(Long id) {
		return usuarioRepository.findById(id).isPresent() ? 
				usuarioRepository.findById(id).get(): null;
	}
	
	public Page<Usuario> buscar(Pageable pageable){
		return usuarioRepository.findAll(pageable);
	}

	public Usuario buscarByEmail( String email ){
		return usuarioRepository.findByEmail(email);
	}
	
	public void salvar(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());
		if(usuario == null) {
			usuario = new Usuario();
			usuario.setEmail(usuarioDTO.getEmail());
			//String senha = new BCryptPasswordEncoder().encode(usuarioDTO.getSenha());
			String senha = usuarioDTO.getSenha();
			usuario.setSenha(senha);
			usuarioRepository.save(usuario);
		} else {
			throw new DuplicatedEntryException("Já existe um usuário cadastrado com esses dados!");
		}
		
		Tipo_Usuario tipo_Usuario = tipoUsuarioRepository.findByNome("comum");
		
		Pessoa pessoa = pessoaRepository.findByCpf(usuarioDTO.getCpf());
		if(pessoa == null) {
			pessoa = new Pessoa();
			pessoa.setNome(usuarioDTO.getNome());
			pessoa.setCpf(usuarioDTO.getCpf());
			pessoa.setTipo_usuario(tipo_Usuario);
			pessoa.setUsuario(usuario);
			pessoaRepository.save(pessoa);
		} else {
			throw new DuplicatedEntryException("Já existe uma pessoa cadastrada com esses dados!");
		}
	
	}
	
	
}
