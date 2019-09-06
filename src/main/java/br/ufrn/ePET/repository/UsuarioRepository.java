package br.ufrn.ePET.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.ufrn.ePET.models.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	Usuario findByEmail(String email);
}
