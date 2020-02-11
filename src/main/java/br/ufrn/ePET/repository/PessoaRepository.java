package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Usuario;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	@Query(value = "SELECT * FROM pessoa p WHERE p.nome LIKE %?1% OR p.cpf LIKE %?1%", nativeQuery = true)
	Page<Pessoa> findByNomeOrCPF(String search, Pageable pageable);
	
	Pessoa findByUsuario(Usuario usuario);
	
	Pessoa findByCpf(String cpf);
}
