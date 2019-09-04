package br.ufrn.ePET.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Tipo_Usuario;

public interface Tipo_UsuarioRepository extends JpaRepository<Tipo_Usuario, Long>{

	Tipo_Usuario findById(Integer id);
	
	Tipo_Usuario findByNome(String nome);
	
	void deleteById(Integer id);
	
}
