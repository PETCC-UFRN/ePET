package br.ufrn.ePET.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Usuario;
import br.ufrn.ePET.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario buscar(Long id) {
		return usuarioRepository.findById(id).isPresent() ? 
				usuarioRepository.findById(id).get(): null;
	}
	
	public Usuario buscarByEmail( String email ){
		return usuarioRepository.findByEmail(email);
	}
	
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	
}
