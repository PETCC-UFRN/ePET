package br.ufrn.ePET.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	@Query(value = "SELECT * FROM pessoa WHERE idPessoa = :id", nativeQuery = true)
	Optional<Pessoa> findById(Integer id);
	
	@Query(value = "SELECT * FROM pessoa WHERE nome = :nome")
	void createPessoa(Pessoa p);
	
	@Query(value = "SELECT * FROM pessoa WHERE nome = :nome")
	Pessoa findByNome(String nome);
	
	@Query(value= "DELETE * FROM pessoa WHERE idPessoa = :idPessoa")
	void deleteById(Integer idPessoa);
		
}
