package br.ufrn.ePET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Tipo_Usuario;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;

@Service
public class Tipo_UsuarioService {
	
	private final Tipo_UsuarioRepository tipo_UsuarioRepository;

	@Autowired
	public Tipo_UsuarioService(Tipo_UsuarioRepository tipo_UsuarioRepository) {
		this.tipo_UsuarioRepository = tipo_UsuarioRepository;
	}
	
	public Tipo_Usuario buscar(Long id) {
		try {
			return tipo_UsuarioRepository.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Tipo_Usuario> buscar(){
		try {
			return tipo_UsuarioRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Tipo_Usuario salvar(Long id, Tipo_Usuario tipoUsuario){
		try {
			return tipo_UsuarioRepository.save(tipoUsuario);
		} catch (Exception e) {
			return null;
		}
	}

}
