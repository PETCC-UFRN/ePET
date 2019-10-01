package br.ufrn.ePET.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.models.Petiano;

public interface PetianoRepository extends JpaRepository<Petiano, Long>{
	//Petiano findbyPessoa(Pessoa pessoa);
	@Query(value = "SELECT * FROM petiano p WHERE p.data_egresso IS NULL", nativeQuery = true)
	Page<Petiano> findByAtuais(Pageable pageable);
	
	@Query(value = "SELECT * FROM petiano p WHERE p.data_egresso IS NOT NULL", nativeQuery = true)
	Page<Petiano> findByAntigos(Pageable pageable);
}
