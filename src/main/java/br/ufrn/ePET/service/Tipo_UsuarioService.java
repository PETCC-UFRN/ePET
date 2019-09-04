package br.ufrn.ePET.service;

import org.springframework.stereotype.Service;

import br.ufrn.ePET.models.Tipo_Usuario;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;

@Service
public class Tipo_UsuarioService {
	
	private Tipo_UsuarioRepository type_user_Repository;
	
	public Tipo_UsuarioService(Tipo_UsuarioRepository tipo_repository) {
		type_user_Repository = tipo_repository;
	}
	
	public void save(Tipo_Usuario p) {
		this.type_user_Repository.save(p);
	}
	
	public Tipo_Usuario findById(Integer id) {
		return type_user_Repository.findById(id);
	}
	
	public void deleteTipoUsuario(Integer id) {
		this.type_user_Repository.deleteById(id);
	}
	

}
